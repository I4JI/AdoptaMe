package mx.edu.unpa.apidroid.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "imagen_mascota")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagenMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mascota", nullable = false)
    private Mascota mascota;

    @Column(name = "url_imagen", nullable = false, length = 255)
    private String urlImagen;

    @Builder.Default
    @Column(name = "imagen_principal", nullable = false)
    private Boolean imagenPrincipal = false;

    @CreationTimestamp
    @Column(name = "fecha_subida", updatable = false)
    private LocalDateTime fechaSubida;
}