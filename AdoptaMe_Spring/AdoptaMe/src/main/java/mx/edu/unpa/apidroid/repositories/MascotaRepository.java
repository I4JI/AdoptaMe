package mx.edu.unpa.apidroid.repositories;

import mx.edu.unpa.apidroid.domains.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByActivoTrue();
    List<Mascota> findByTipoMascota_IdTipoMascotaAndActivoTrue(Long idTipoMascota);
}