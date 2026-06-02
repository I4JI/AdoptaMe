package mx.edu.unpa.apidroid.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cat_tipo_mascota")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatTipoMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoMascota;

    @Column(nullable = false, length = 50)
    private String descripcion;

    @Builder.Default
    @Column(nullable = false)
    private Boolean activo = true;
}