package mx.edu.unpa.apidroid.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImagenMascotaService {
    String subirImagen(Long idMascota, MultipartFile file, Boolean principal);
}