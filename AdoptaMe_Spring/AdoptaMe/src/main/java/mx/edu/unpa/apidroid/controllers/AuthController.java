package mx.edu.unpa.apidroid.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.unpa.apidroid.dtos.requests.LoginRequest;
import mx.edu.unpa.apidroid.dtos.requests.UsuarioRequest;
import mx.edu.unpa.apidroid.dtos.responses.AuthResponse;
import mx.edu.unpa.apidroid.dtos.responses.UsuarioResponse;
import mx.edu.unpa.apidroid.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(authService.registrar(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}