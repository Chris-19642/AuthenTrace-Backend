    package com.upc.appauthentrace.security.util;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Component;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;
    import org.springframework.web.context.request.WebRequest;

    import java.time.OffsetDateTime;
    import java.util.*;
    import java.util.function.Function;

    @Component
    public class JwtUtil {
        @Value("${jwt.secret}")
        private String secretKey;

        public String extractUsername(String token) {
            return extractClaim(token, Claims::getSubject);
        }

        public Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }

        private Claims extractAllClaims(String token) {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        }

        private Boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        public String generateToken(UserDetails userDetails) {
            Map<String, Object> claims = new HashMap<>();
            return createToken(claims, userDetails.getUsername());
        }

        private String createToken(Map<String, Object> claims, String subject) {
            return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20))
                    .signWith(SignatureAlgorithm.HS512, secretKey).compact();
        }

        public Boolean validateToken(String token, UserDetails userDetails) {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

        @RestControllerAdvice
        public class ValidationHandler {

            @ExceptionHandler(MethodArgumentNotValidException.class)
            public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
                List<Map<String, Object>> errors = new ArrayList<>();

                ex.getBindingResult().getFieldErrors().forEach(error -> {
                    Map<String, Object> err = new LinkedHashMap<>();
                    err.put("objectName", error.getObjectName());
                    err.put("field", error.getField());
                    err.put("rejectedValue", error.getRejectedValue());
                    err.put("codes", error.getCodes());
                    err.put("arguments", error.getArguments());
                    err.put("defaultMessage", error.getDefaultMessage());
                    err.put("bindingFailure", error.isBindingFailure());
                    err.put("code", error.getCode());
                    errors.add(err);
                });

                Map<String, Object> body = new LinkedHashMap<>();
                body.put("timestamp", OffsetDateTime.now().toString());
                body.put("status", HttpStatus.BAD_REQUEST.value());
                body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
                body.put("message", "Validation failed for object='" + ex.getBindingResult().getObjectName()
                        + "'. Error count: " + ex.getBindingResult().getErrorCount());
                body.put("errors", errors);
                body.put("path", request.getDescription(false).replace("uri=", ""));

                return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
            }
        }
    }
