package com.upc.appauthentrace.security.controllers;

import com.upc.appauthentrace.security.dtos.UserDTO;
import com.upc.appauthentrace.security.entities.Role;
import com.upc.appauthentrace.security.entities.User;
import com.upc.appauthentrace.security.repository.RoleRepository;
import com.upc.appauthentrace.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder bcrypt;

    /*Usados anteriormente en Usuario*/
    @Autowired
    private RoleRepository roleRepository;

    //Este registar se hace desde el frontend (crear cuenta)
    @PostMapping("/registro")
    public ResponseEntity<UserDTO> registrar(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registrar(userDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> listarUsuarios() {
        return userService.listarUsuarios();
    }

    @GetMapping("buscar/{id}")
    public UserDTO buscar(@PathVariable Long idUsuario) {
        User user = userService.findById(idUsuario);
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getIdUsuario(),
                user.getNombre(),
                user.getApellido(),
                user.getCorreo(),
                user.getUsername(),
                null, // password no se envía
                user.getBloqueado(),
                null  // roles si quieres incluirlos
        );
    }


    // Editar perfil (usuario)
    @PutMapping("/perfil/{id}")
    public ResponseEntity<UserDTO> editarPerfil(@PathVariable Long idUsuario, @RequestBody UserDTO userDTO) {
        userDTO.setIdUsuario(idUsuario);
        return ResponseEntity.ok(userService.editarPerfilUsuario(userDTO));
    }

    // Editar completo (admin)
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> editarAdmin(@PathVariable Long idUsuario, @RequestBody UserDTO userDTO) {
        userDTO.setIdUsuario(idUsuario);
        return ResponseEntity.ok(userService.editarAdmin(userDTO));
    }

    // Bloquear usuario (admin)
    @PutMapping("/bloquear/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> bloquear(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(userService.bloquear(idUsuario));
    }

    // Eliminar usuario (admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar(@PathVariable Long idUsuario) {
        userService.eliminar(idUsuario);
    }

    // Buscar por correo
    @GetMapping("/buscar")
    public UserDTO buscarPorCorreo(@RequestParam String correo) {
        return userService.buscarPorCorreo(correo);
    }

    @PostMapping("/assign/{userId}/{rolId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> assignRole(@PathVariable Long userId, @PathVariable Long rolId) {
        return ResponseEntity.ok(userService.insertUserRol(userId, rolId));
    }

    /*Anteriores*/
    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public void createUser(@RequestBody User user) {
        String bcryptPassword = bcrypt.encode(user.getPassword());
        user.setPassword(bcryptPassword);
        userService.save(user);
    }

    @PostMapping("/rol")
    @PreAuthorize("hasRole('ADMIN')")
    public void createRol(@RequestBody Role rol) {
        userService.grabar(rol);
    }


    @PostMapping("/save/{user_id}/{rol_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> saveUseRol(@PathVariable("user_id") Long user_id,
                                              @PathVariable("rol_id") Long rol_id){
        return new ResponseEntity<Integer>(userService.insertUserRol(user_id, rol_id), HttpStatus.OK);
    }
}
