package mx.edu.unpa.apidroid.dtos.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MascotaResponse {
    private Long idMascota;
    private Long idUsuarioDonador;
    private Long idTipoMascota;
    private String tipoMascota;
    private String nombre;
    private String raza;
    private String sexo;
    private String edadAproximada;
    private String descripcion;
    private String estadoAdopcion;
    private Boolean activo;
    private LocalDateTime fechaPublicacion;
    private List<String> imagenes;
}