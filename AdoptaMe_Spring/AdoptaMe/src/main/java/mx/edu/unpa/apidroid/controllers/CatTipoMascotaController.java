package mx.edu.unpa.apidroid.controllers;

import lombok.RequiredArgsConstructor;
import mx.edu.unpa.apidroid.dtos.responses.TipoMascotaResponse;
import mx.edu.unpa.apidroid.services.CatTipoMascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-mascota")
@RequiredArgsConstructor
public class CatTipoMascotaController {

    private final CatTipoMascotaService service;

    @GetMapping
    public ResponseEntity<List<TipoMascotaResponse>> obtenerTipos() {
        return ResponseEntity.ok(service.obtenerTiposActivos());
    }
}