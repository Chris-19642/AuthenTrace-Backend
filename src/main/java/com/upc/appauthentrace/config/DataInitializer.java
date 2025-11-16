package com.upc.appauthentrace.config;

import com.upc.appauthentrace.security.entities.Role;
import com.upc.appauthentrace.security.entities.User;
import com.upc.appauthentrace.security.repository.RoleRepository;
import com.upc.appauthentrace.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Crear roles si no existen
        Role rolUser = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        Role rolAdmin = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        // Crear usuario normal inicial
        if (!userRepository.existsByUsername("user1")) {

            User u = new User();
            u.setUsername("user1");
            u.setPassword(passwordEncoder.encode("12345"));

            u.setNombre("Usuario");
            u.setApellido("Inicial");
            u.setCorreo("user1@example.com");
            u.setFechaCreacion(LocalDate.now());
            u.setBloqueado(false);

            u.getRoles().add(rolUser);
            userRepository.save(u);
        }

        // Crear administrador inicial
        if (!userRepository.existsByUsername("admin")) {

            User a = new User();
            a.setUsername("admin");
            a.setPassword(passwordEncoder.encode("adminpass"));

            a.setNombre("Admin");
            a.setApellido("Principal");
            a.setCorreo("admin@example.com");
            a.setFechaCreacion(LocalDate.now());
            a.setBloqueado(false);

            a.getRoles().add(rolAdmin);
            userRepository.save(a);
        }
    }
}

