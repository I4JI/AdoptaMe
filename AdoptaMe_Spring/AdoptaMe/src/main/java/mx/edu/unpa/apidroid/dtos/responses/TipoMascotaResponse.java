package mx.edu.unpa.apidroid.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoMascotaResponse {
    private Long idTipoMascota;
    private String descripcion;
}