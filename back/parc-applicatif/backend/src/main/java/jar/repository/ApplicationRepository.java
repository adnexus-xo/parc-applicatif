package jar.repository;

import jar.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import java.util.List;

//@Repository

public interface ApplicationRepository extends JpaRepository <Application, Integer> {
    List<Application> findByEquipeUtilisatriceIdEquipe(Integer idEquipe);
    List<Application> findByCategorieIdCategorie(Integer idCategorie);
    List<Application> findByEtat(String etat);
}