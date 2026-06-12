package jar.service;

import jar.entity.Categorie;
import jar.repository.CategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    public Categorie findById(Integer id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categorie non trouvée"));
    }

    public Categorie save(Categorie categorie) {
        return categorieRepository.save(categorie   );
    }

    public void delete(Integer id) {
        categorieRepository.deleteById(id);
    }
}