package jar.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie")
    private Integer idCategorie;

    @Column(name = "nom_categorie", nullable = false, unique = true, length = 50)
    private String nomCategorie;

    @Column(name = "description_categorie", nullable = false, length = 100)
    private String descriptionCategorie;
}