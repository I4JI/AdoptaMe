package mx.edu.unpa.apidroid.services.impl;

import mx.edu.unpa.apidroid.domains.Usuario;
import mx.edu.unpa.apidroid.dtos.requests.LoginRequest;
import mx.edu.unpa.apidroid.dtos.requests.UsuarioRequest;
import mx.edu.unpa.apidroid.dtos.responses.AuthResponse;
import mx.edu.unpa.apidroid.dtos.responses.UsuarioResponse;
import mx.edu.unpa.apidroid.repositories.UsuarioRepository;
import mx.edu.unpa.apidroid.services.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponse registrar(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .email(request.getEmail().toLowerCase())
                .telefono(request.getTelefono())
                .password(passwordEncoder.encode(request.getPassword()))
                .activo(true)
                .build();

        Usuario guardado = usuarioRepository.save(usuario);

        return UsuarioResponse.builder()
                .id(guardado.getIdUsuario())
                .nombre(guardado.getNombre())
                .apellidoPaterno(guardado.getApellidoPaterno())
                .apellidoMaterno(guardado.getApellidoMaterno())
                .email(guardado.getEmail())
                .telefono(guardado.getTelefono())
                .activo(guardado.getActivo())
                .fechaRegistro(guardado.getFechaRegistro())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return AuthResponse.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .mensaje("Login exitoso")
                .build();
    }
}