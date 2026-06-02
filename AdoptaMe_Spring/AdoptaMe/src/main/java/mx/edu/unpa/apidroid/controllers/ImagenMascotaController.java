package mx.edu.unpa.apidroid.controllers;

import lombok.RequiredArgsConstructor;
import mx.edu.unpa.apidroid.services.ImagenMascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/imagenes-mascota")
@RequiredArgsConstructor
public class ImagenMascotaController {

    private final ImagenMascotaService imagenMascotaService;

    @PostMapping("/upload/{idMascota}")
    public ResponseEntity<String> subirImagen(
            @PathVariable Long idMascota,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "principal", required = false, defaultValue = "false") Boolean principal
    ) {
        return ResponseEntity.ok(imagenMascotaService.subirImagen(idMascota, file, principal));
    }
}