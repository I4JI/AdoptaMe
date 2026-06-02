package mx.edu.unpa.apidroid.services.impl;

import lombok.RequiredArgsConstructor;
import mx.edu.unpa.apidroid.dtos.responses.TipoMascotaResponse;
import mx.edu.unpa.apidroid.repositories.CatTipoMascotaRepository;
import mx.edu.unpa.apidroid.services.CatTipoMascotaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatTipoMascotaServiceImpl implements CatTipoMascotaService {

    private final CatTipoMascotaRepository repository;

    @Override
    public List<TipoMascotaResponse> obtenerTiposActivos() {
        return repository.findByActivoTrue().stream()
                .map(tipo -> TipoMascotaResponse.builder()
                        .idTipoMascota(tipo.getIdTipoMascota())
                        .descripcion(tipo.getDescripcion())
                        .build())
                .toList();
    }
}