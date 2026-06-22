package jar.service;

import jar.entity.Equipe;
import jar.repository.EquipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipeService {

    private final EquipeRepository equipeRepository;

    public List<Equipe> findAll() {
        return equipeRepository.findAll();
    }

    public Equipe findById(Integer id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipe non trouvée"));
    }

    public Equipe save(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    public void delete(Integer id) {
        equipeRepository.deleteById(id);
    }
}