package mx.edu.unpa.apidroid.repositories;

import mx.edu.unpa.apidroid.domains.CatTipoMascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatTipoMascotaRepository extends JpaRepository<CatTipoMascota, Long> {
    List<CatTipoMascota> findByActivoTrue();
}