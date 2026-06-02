package mx.edu.unpa.apidroid.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String foto;
    private String telefono;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
}
