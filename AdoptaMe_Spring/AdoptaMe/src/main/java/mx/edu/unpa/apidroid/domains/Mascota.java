package mx.edu.unpa.apidroid.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mascota")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMascota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_donador", nullable = false)
    private Usuario usuarioDonador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_mascota", nullable = false)
    private CatTipoMascota tipoMascota;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 100)
    private String raza;

    @Column(nullable = false, length = 20)
    private String sexo;

    @Column(name = "edad_aproximada", length = 50)
    private String edadAproximada;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Builder.Default
    @Column(name = "estado_adopcion", nullable = false, length = 30)
    private String estadoAdopcion = "Disponible";

    @Builder.Default
    @Column(nullable = false)
    private Boolean activo = true;

    @CreationTimestamp
    @Column(name = "fecha_publicacion", updatable = false)
    private LocalDateTime fechaPublicacion;

    @Builder.Default
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenMascota> imagenes = new ArrayList<>();
}