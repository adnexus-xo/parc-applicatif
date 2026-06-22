package jar.repository;

import jar.entity.DependDe;
import jar.entity.DependDeId;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;   

//@Repository
public interface DependDeRepository extends JpaRepository<DependDe, DependDeId> {
    
}