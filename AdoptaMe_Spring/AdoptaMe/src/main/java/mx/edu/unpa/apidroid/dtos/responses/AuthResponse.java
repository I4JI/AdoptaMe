package mx.edu.unpa.apidroid.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private Long idUsuario;
    private String nombre;
    private String email;
    private String mensaje;
}