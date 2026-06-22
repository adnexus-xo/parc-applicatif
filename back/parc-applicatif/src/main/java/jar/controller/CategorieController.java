package jar.controller;

import jar.entity.Categorie;
import jar.service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    public List<Categorie> findAll() {
        return categorieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(categorieService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Categorie> save(@RequestBody Categorie categorie) {
        return ResponseEntity.ok(categorieService.save(categorie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categorieService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Categorie> update(@PathVariable Integer id, @RequestBody Categorie categorie) {
        categorie.setIdCategorie(id);
        return ResponseEntity.ok(categorieService.save(categorie));
    } 
}