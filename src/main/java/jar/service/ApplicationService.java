package jar.service;

import jar.entity.Application;
import jar.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    public Application findById(Integer id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application non trouvée"));
    }

    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    public void delete(Integer id) {
        applicationRepository.deleteById(id);
    }

    public List<Application> findByEquipeId(Integer equipeId) {
        return applicationRepository.findByEquipeUtilisatriceIdEquipe(equipeId);
    }

    public List<Application> findByCategorieId(Integer categorieId) {
        return applicationRepository.findByCategorieIdCategorie(categorieId);
    }

    public List<Application> findByEtat(String etat) {
        return applicationRepository.findByEtat(etat);
    }
}