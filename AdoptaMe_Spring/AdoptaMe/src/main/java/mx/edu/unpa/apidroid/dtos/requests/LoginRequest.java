package mx.edu.unpa.apidroid.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    @Email
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "Solo se permiten correos Gmail")
    private String email;

    @NotBlank
    private String password;
}