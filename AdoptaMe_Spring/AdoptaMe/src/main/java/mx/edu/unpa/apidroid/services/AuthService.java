package mx.edu.unpa.apidroid.services;

import mx.edu.unpa.apidroid.dtos.requests.LoginRequest;
import mx.edu.unpa.apidroid.dtos.requests.UsuarioRequest;
import mx.edu.unpa.apidroid.dtos.responses.AuthResponse;
import mx.edu.unpa.apidroid.dtos.responses.UsuarioResponse;

public interface AuthService {
    UsuarioResponse registrar(UsuarioRequest request);
    AuthResponse login(LoginRequest request);
}