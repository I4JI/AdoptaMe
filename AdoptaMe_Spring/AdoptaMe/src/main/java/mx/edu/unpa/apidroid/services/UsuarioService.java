package mx.edu.unpa.apidroid.services;

import mx.edu.unpa.apidroid.dtos.requests.UsuarioRequest;
import mx.edu.unpa.apidroid.dtos.responses.UsuarioResponse;

import java.util.List;

public interface UsuarioService {
    UsuarioResponse crearUsuario(UsuarioRequest request);
    UsuarioResponse obtenerPorId(Long id);
    List<UsuarioResponse> obtenerTodos();
    UsuarioResponse actualizarUsuario(Long id, UsuarioRequest request);
    void eliminarUsuario(Long id); // O desactivar (Soft Delete)
    UsuarioResponse obtenerUsuarioByEmail(String email);
}
