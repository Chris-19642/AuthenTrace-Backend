package com.upc.appauthentrace.security.services;

import com.upc.appauthentrace.security.dtos.UserDTO;
import com.upc.appauthentrace.security.entities.Role;
import com.upc.appauthentrace.security.entities.User;
import com.upc.appauthentrace.security.interfaces.IUserService;
import com.upc.appauthentrace.security.repository.RoleRepository;
import com.upc.appauthentrace.security.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    /*Usados anteriormente en Usuario*/
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder bcrypt;

    @Override
    public User findById(Long idUsuario) {
        return userRepository.findById(idUsuario).orElse(null);
    }

    @Override
    @Transactional
    public UserDTO registrar(UserDTO userDTO) {
        if (userDTO.getIdUsuario() == null) {
            User user = modelMapper.map(userDTO, User.class);

            // Asegurarse de que roles no sea null
            if (user.getRoles() == null) {
                user.setRoles(new HashSet<>());
            }

            user.setPassword(bcrypt.encode(user.getPassword()));
            user.setFechaCreacion(LocalDate.now());
            user.setBloqueado(false);

            Role rolUser = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Rol ROLE_USER no encontrado"));
            user.getRoles().add(rolUser);

            User saved = userRepository.save(user);
            return modelMapper.map(saved, UserDTO.class);
        }
        return null;
    }

    @Override
    @Transactional
    public UserDTO editarPerfilUsuario(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setNombre(userDTO.getNombre());
        user.setApellido(userDTO.getApellido());
        user.setCorreo(userDTO.getCorreo());
        user.setUsername(userDTO.getUsername());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(bcrypt.encode(userDTO.getPassword()));
        }

        User updated = userRepository.save(user);
        return modelMapper.map(updated, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO editarAdmin(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setNombre(userDTO.getNombre());
        user.setApellido(userDTO.getApellido());
        user.setCorreo(userDTO.getCorreo());
        user.setUsername(userDTO.getUsername());
        user.setBloqueado(userDTO.isBloqueado());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(bcrypt.encode(userDTO.getPassword()));
        }

        // Actualizar roles
        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            Set<Role> roles = userDTO.getRoles().stream()
                    .map(r -> roleRepository.findByName(r.getName())
                            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + r.getName())))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        User updated = userRepository.save(user);
        return modelMapper.map(updated, UserDTO.class);
    }

    // Bloquear usuario
    @Override
    @Transactional
    public UserDTO bloquear(Long idUsuario) {
        User user = userRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setBloqueado(true);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public void eliminar(Long idUsuario) {
        userRepository.deleteById(idUsuario);
    }

    @Override
    public List<UserDTO> listarUsuarios() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO buscarPorCorreo(String correo) {
        return userRepository.findByCorreo(correo)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElse(null);
    }



    /*Para pruebas básicas*/
    //El administrador crea un usuario manualmente
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    //El administrador crea un nuevo rol
    @Transactional
    public void grabar(Role role) {
        roleRepository.save(role);
    }

    public Integer insertUserRol(Long user_id, Long rol_id) {
        Integer result = 0;
        userRepository.insertUserRol(user_id, rol_id);
        return 1;
    }
}

