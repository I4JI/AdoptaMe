package mx.edu.unpa.apidroid.services;

import mx.edu.unpa.apidroid.dtos.responses.TipoMascotaResponse;

import java.util.List;

public interface CatTipoMascotaService {
    List<TipoMascotaResponse> obtenerTiposActivos();
}