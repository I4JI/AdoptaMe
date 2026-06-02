package mx.edu.unpa.apidroid.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MascotaRequest {

    @NotNull
    private Long idUsuarioDonador;

    @NotNull
    private Long idTipoMascota;

    @NotBlank
    private String nombre;

    private String raza;

    @NotBlank
    private String sexo;

    private String edadAproximada;

    private String descripcion;

    private String estadoAdopcion;
}