package jar.service;

import jar.entity.Application;
import jar.entity.Historique;
import jar.entity.Utilisateur;
import jar.repository.ApplicationRepository;
import jar.repository.HistoriqueRepository;
import jar.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final HistoriqueRepository historiqueRepository;
    private final UtilisateurRepository utilisateurRepository;

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    public Application findById(Integer id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application non trouvée"));
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

    public Application save(Application application) {
    if (application.getIdApplication() != null) {
        Application ancienne = applicationRepository.findById(application.getIdApplication())
            .orElse(null);
        
        if (ancienne != null && !ancienne.getEtat().equals(application.getEtat())) {
            String ancienEtat = ancienne.getEtat();
            String nouvelEtat = application.getEtat();
            
            Application sauvegardee = applicationRepository.save(application);
            
            Historique historique = new Historique();
            historique.setApplication(sauvegardee);
            historique.setAncienEtat(ancienEtat);
            historique.setNouvelEtat(nouvelEtat);
            historique.setDateChangementEtat(LocalDateTime.now());
            
            Utilisateur utilisateur = utilisateurRepository.findAll().get(0);
            historique.setUtilisateur(utilisateur);
            
            historiqueRepository.save(historique);
            
            return sauvegardee;
        }
    }
    return applicationRepository.save(application);
    }
}