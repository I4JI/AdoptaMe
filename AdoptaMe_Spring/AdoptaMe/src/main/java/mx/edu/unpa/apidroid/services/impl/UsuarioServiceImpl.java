package mx.edu.unpa.apidroid.services.impl;

import lombok.RequiredArgsConstructor;
import mx.edu.unpa.apidroid.domains.Usuario;
import mx.edu.unpa.apidroid.dtos.requests.UsuarioRequest;
import mx.edu.unpa.apidroid.dtos.responses.UsuarioResponse;
import mx.edu.unpa.apidroid.repositories.UsuarioRepository;
import mx.edu.unpa.apidroid.services.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        // Aquí idealmente deberías encriptar el password (ej. con BCryptPasswordEncoder)
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .password(request.getPassword()) // ¡Encriptar en la vida real!
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapearAResponse(usuarioGuardado);
    }

    @Override
    public UsuarioResponse obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return mapearAResponse(usuario);
    }

    @Override
    public List<UsuarioResponse> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::mapearAResponse)
                .toList();
    }

    @Override
    public UsuarioResponse actualizarUsuario(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setNombre(request.getNombre());
        usuario.setApellidoPaterno(request.getApellidoPaterno());
        usuario.setApellidoMaterno(request.getApellidoMaterno());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());

        // Solo actualizamos password si viene en el request
        if(request.getPassword() != null && !request.getPassword().isEmpty()) {
            usuario.setPassword(request.getPassword()); // ¡Encriptar!
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return mapearAResponse(usuarioActualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Soft delete (recomendado):
        usuario.setActivo(false);
        usuarioRepository.save(usuario);

        // Hard delete (borrado físico, descomenta si lo prefieres):
        // usuarioRepository.delete(usuario);
    }

    @Override
    public UsuarioResponse obtenerUsuarioByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        return mapearAResponse(usuario);
    }

    private UsuarioResponse mapearAResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .apellidoPaterno(usuario.getApellidoPaterno())
                .apellidoMaterno(usuario.getApellidoMaterno())
                .email(usuario.getEmail())
                //.foto(usuario.getFoto())
                .telefono(usuario.getTelefono())
                .activo(usuario.getActivo())
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }
}
