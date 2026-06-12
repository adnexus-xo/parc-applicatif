package jar.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historique")
public class Historique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historique")
    private Integer idHistorique;

    @Column(name = "ancien_etat", nullable = false, length = 50)
    private String ancienEtat;

    @Column(name = "nouvel_etat", nullable = false, length = 50)
    private String nouvelEtat;

    @Column(name = "date_changement_etat", nullable = false)
    private LocalDateTime dateChangementEtat;

    @ManyToOne
    @JoinColumn(name = "id_application", nullable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;
}