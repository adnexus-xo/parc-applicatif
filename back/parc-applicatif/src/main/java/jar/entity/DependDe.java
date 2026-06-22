package jar.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "depend_de")
public class DependDe {

    @EmbeddedId
    private DependDeId id;

    @ManyToOne
    @MapsId("idApplicationSource")
    @JoinColumn(name = "id_application_source", nullable = false)
    private Application applicationSource;

    @ManyToOne
    @MapsId("idApplicationCible")
    @JoinColumn(name = "id_application_cible", nullable = false)
    private Application applicationCible;

    @Column(name = "type_dependance", nullable = false, length = 50)
    private String typeDependance;

    @Column(name = "description_dependance", columnDefinition = "TEXT")
    private String descriptionDependance;

    @Column(name = "date_creation_dependance", nullable = false)
    private LocalDateTime dateCreationDependance;
}