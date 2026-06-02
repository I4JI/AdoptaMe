package mx.edu.unpa.apidroid.services;

import mx.edu.unpa.apidroid.dtos.requests.MascotaRequest;
import mx.edu.unpa.apidroid.dtos.responses.MascotaResponse;

import java.util.List;

public interface MascotaService {
    MascotaResponse registrarMascota(MascotaRequest request);
    List<MascotaResponse> obtenerTodas();
    List<MascotaResponse> obtenerPorTipo(Long idTipoMascota);
    MascotaResponse obtenerPorId(Long idMascota);
}