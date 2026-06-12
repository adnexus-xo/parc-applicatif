package jar.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_application")
    private Integer idApplication;

    @Column(name = "nom_application", nullable = false, unique = true, length = 50)
    private String nomApplication;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "version", nullable = false, length = 10)
    private String version;

    @Column(name = "etat", nullable = false, length = 50)
    private String etat;

    @Column(name = "criticite", nullable = false, length = 20)
    private String criticite;

    @Column(name = "date_creation_fiche", nullable = false)
    private LocalDateTime dateCreationFiche;

    @Column(name = "date_mise_en_service")
    private LocalDate dateMiseEnService;

    @Column(name = "date_obsolescence")
    private LocalDate dateObsolescence;

    @ManyToOne
    @JoinColumn(name = "id_categorie", nullable = false)
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "id_equipe_utilisatrice", nullable = false)
    private Equipe equipeUtilisatrice;

    @ManyToOne
    @JoinColumn(name = "id_equipe_responsable", nullable = false)
    private Equipe equipeResponsable;
}