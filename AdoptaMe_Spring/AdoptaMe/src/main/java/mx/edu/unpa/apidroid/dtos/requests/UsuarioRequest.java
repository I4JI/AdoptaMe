package mx.edu.unpa.apidroid.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsuarioRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellidoPaterno;

    private String apellidoMaterno;

    @NotBlank
    @Email
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "Solo se permiten correos Gmail")
    private String email;

    private String telefono;

    @NotBlank
    private String password;
}