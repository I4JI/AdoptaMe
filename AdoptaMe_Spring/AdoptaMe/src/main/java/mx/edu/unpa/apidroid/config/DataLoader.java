package mx.edu.unpa.apidroid.config;

import mx.edu.unpa.apidroid.domains.CatTipoMascota;
import mx.edu.unpa.apidroid.repositories.CatTipoMascotaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initTiposMascota(CatTipoMascotaRepository repository) {
        return args -> {
            registrarSiNoExiste(repository, "Perro");
            registrarSiNoExiste(repository, "Gato");
            registrarSiNoExiste(repository, "Loro");
            registrarSiNoExiste(repository, "Hamster");
        };
    }

    private void registrarSiNoExiste(CatTipoMascotaRepository repository, String descripcion) {
        boolean existe = repository.findByActivoTrue().stream()
                .anyMatch(t -> t.getDescripcion().equalsIgnoreCase(descripcion));

        if (!existe) {
            repository.save(CatTipoMascota.builder()
                    .descripcion(descripcion)
                    .activo(true)
                    .build());
        }
    }
}