package mx.edu.unpa.apidroid.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.unpa.apidroid.dtos.requests.MascotaRequest;
import mx.edu.unpa.apidroid.dtos.responses.MascotaResponse;
import mx.edu.unpa.apidroid.services.MascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<MascotaResponse> registrar(@Valid @RequestBody MascotaRequest request) {
        return new ResponseEntity<>(mascotaService.registrarMascota(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponse>> listarTodas() {
        return ResponseEntity.ok(mascotaService.obtenerTodas());
    }

    @GetMapping("/tipo/{idTipoMascota}")
    public ResponseEntity<List<MascotaResponse>> listarPorTipo(@PathVariable Long idTipoMascota) {
        return ResponseEntity.ok(mascotaService.obtenerPorTipo(idTipoMascota));
    }

    @GetMapping("/{idMascota}")
    public ResponseEntity<MascotaResponse> obtenerPorId(@PathVariable Long idMascota) {
        return ResponseEntity.ok(mascotaService.obtenerPorId(idMascota));
    }
}