package jar.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "equipe")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipe")
    private Integer idEquipe;

    @Column(name = "nom_equipe", nullable = false, unique = true, length = 50)
    private String nomEquipe;
}