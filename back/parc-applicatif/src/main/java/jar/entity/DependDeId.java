package jar.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class DependDeId implements Serializable {
    private Integer idApplicationSource;
    private Integer idApplicationCible;
}