package jar.service;

import jar.entity.Historique;
import jar.repository.HistoriqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriqueService {

    private final HistoriqueRepository historiqueRepository;

    public List<Historique> findAll() {
        return historiqueRepository.findAll();
    }

    public Historique findById(Integer id) {
        return historiqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historique non trouvée"));
    }

    public Historique save(Historique historique) {
        return historiqueRepository.save(historique);
    }

    public void delete(Integer id) {
        historiqueRepository.deleteById(id);
    }
}