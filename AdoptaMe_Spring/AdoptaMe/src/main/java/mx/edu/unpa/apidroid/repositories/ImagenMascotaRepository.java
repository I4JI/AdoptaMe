package mx.edu.unpa.apidroid.repositories;

import mx.edu.unpa.apidroid.domains.ImagenMascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenMascotaRepository extends JpaRepository<ImagenMascota, Long> {
    List<ImagenMascota> findByMascota_IdMascota(Long idMascota);
}