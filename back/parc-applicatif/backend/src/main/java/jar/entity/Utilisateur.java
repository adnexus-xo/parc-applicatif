package jar.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Integer idUtilisateur;

    @Column(name = "nom_utilisateur", nullable = false, length = 50)
    private String nomUtilisateur;

    @Column(name = "prenom_utilisateur", nullable = false, length = 50)
    private String prenomUtilisateur;

    @Column(name = "email", nullable = false, unique = true, length = 254)
    private String email;

    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @ManyToOne
    @JoinColumn(name = "id_equipe")
    private Equipe equipe;
}