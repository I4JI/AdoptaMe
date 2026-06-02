package mx.edu.unpa.apidroid.services.impl;

import lombok.RequiredArgsConstructor;
import mx.edu.unpa.apidroid.domains.CatTipoMascota;
import mx.edu.unpa.apidroid.domains.ImagenMascota;
import mx.edu.unpa.apidroid.domains.Mascota;
import mx.edu.unpa.apidroid.domains.Usuario;
import mx.edu.unpa.apidroid.dtos.requests.MascotaRequest;
import mx.edu.unpa.apidroid.dtos.responses.MascotaResponse;
import mx.edu.unpa.apidroid.repositories.CatTipoMascotaRepository;
import mx.edu.unpa.apidroid.repositories.ImagenMascotaRepository;
import mx.edu.unpa.apidroid.repositories.MascotaRepository;
import mx.edu.unpa.apidroid.repositories.UsuarioRepository;
import mx.edu.unpa.apidroid.services.MascotaService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CatTipoMascotaRepository tipoRepository;
    private final ImagenMascotaRepository imagenRepository;

    @Override
    public MascotaResponse registrarMascota(MascotaRequest request) {
        Usuario donador = usuarioRepository.findById(request.getIdUsuarioDonador())
                .orElseThrow(() -> new RuntimeException("Usuario donador no encontrado"));

        CatTipoMascota tipo = tipoRepository.findById(request.getIdTipoMascota())
                .orElseThrow(() -> new RuntimeException("Tipo de mascota no encontrado"));

        Mascota mascota = Mascota.builder()
                .usuarioDonador(donador)
                .tipoMascota(tipo)
                .nombre(request.getNombre())
                .raza(request.getRaza())
                .sexo(request.getSexo())
                .edadAproximada(request.getEdadAproximada())
                .descripcion(request.getDescripcion())
                .estadoAdopcion(request.getEstadoAdopcion() != null ? request.getEstadoAdopcion() : "Disponible")
                .activo(true)
                .build();

        Mascota guardada = mascotaRepository.save(mascota);
        return mapear(guardada);
    }

    @Override
    public List<MascotaResponse> obtenerTodas() {
        return mascotaRepository.findByActivoTrue().stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    public List<MascotaResponse> obtenerPorTipo(Long idTipoMascota) {
        return mascotaRepository.findByTipoMascota_IdTipoMascotaAndActivoTrue(idTipoMascota)
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    public MascotaResponse obtenerPorId(Long idMascota) {
        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        return mapear(mascota);
    }

    private MascotaResponse mapear(Mascota mascota) {
        List<String> imagenes = imagenRepository.findByMascota_IdMascota(mascota.getIdMascota())
                .stream()
                .map(ImagenMascota::getUrlImagen)
                .toList();

        return MascotaResponse.builder()
                .idMascota(mascota.getIdMascota())
                .idUsuarioDonador(mascota.getUsuarioDonador().getIdUsuario())
                .idTipoMascota(mascota.getTipoMascota().getIdTipoMascota())
                .tipoMascota(mascota.getTipoMascota().getDescripcion())
                .nombre(mascota.getNombre())
                .raza(mascota.getRaza())
                .sexo(mascota.getSexo())
                .edadAproximada(mascota.getEdadAproximada())
                .descripcion(mascota.getDescripcion())
                .estadoAdopcion(mascota.getEstadoAdopcion())
                .activo(mascota.getActivo())
                .fechaPublicacion(mascota.getFechaPublicacion())
                .imagenes(imagenes != null ? imagenes : Collections.emptyList())
                .build();
    }
}