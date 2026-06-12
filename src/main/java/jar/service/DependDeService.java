package jar.service;

import jar.entity.DependDe;
import jar.repository.DependDeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import jar.entity.DependDeId;

@Service
@RequiredArgsConstructor
public class DependDeService {

    private final DependDeRepository dependDeRepository;

    public List<DependDe> findAll() {
        return dependDeRepository.findAll();
    }
    
    public DependDe findById(DependDeId id) {
        return dependDeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DependDe non trouvée"));
    }

    public DependDe save(DependDe dependDe) {
        return dependDeRepository.save(dependDe );
    }

    public void delete(DependDeId id) {
        dependDeRepository.deleteById(id);
    }
}