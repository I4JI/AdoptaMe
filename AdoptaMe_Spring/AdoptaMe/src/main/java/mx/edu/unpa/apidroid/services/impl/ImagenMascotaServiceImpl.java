package mx.edu.unpa.apidroid.services.impl;

import lombok.RequiredArgsConstructor;
import mx.edu.unpa.apidroid.domains.ImagenMascota;
import mx.edu.unpa.apidroid.domains.Mascota;
import mx.edu.unpa.apidroid.repositories.ImagenMascotaRepository;
import mx.edu.unpa.apidroid.repositories.MascotaRepository;
import mx.edu.unpa.apidroid.services.ImagenMascotaService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImagenMascotaServiceImpl implements ImagenMascotaService {

    private final MascotaRepository mascotaRepository;
    private final ImagenMascotaRepository imagenMascotaRepository;

    @Override
    public String subirImagen(Long idMascota, MultipartFile file, Boolean principal) {
        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        try {
            Path carpeta = Paths.get(System.getProperty("user.dir"), "uploads", "mascotas");
            Files.createDirectories(carpeta);

            String original = file.getOriginalFilename();
            String extension = original != null && original.contains(".")
                    ? original.substring(original.lastIndexOf("."))
                    : "";

            String nombreArchivo = "mascota_" + idMascota + "_" + System.currentTimeMillis() + extension;
            Path destino = carpeta.resolve(nombreArchivo);
            file.transferTo(destino.toFile());

            String urlRelativa = "/uploads/mascotas/" + nombreArchivo;

            ImagenMascota imagen = ImagenMascota.builder()
                    .mascota(mascota)
                    .urlImagen(urlRelativa)
                    .imagenPrincipal(principal != null && principal)
                    .build();

            imagenMascotaRepository.save(imagen);

            return urlRelativa;
        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen: " + e.getMessage(), e);
        }
    }
}