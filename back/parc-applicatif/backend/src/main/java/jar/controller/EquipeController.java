package jar.controller;

import jar.entity.Equipe;
import jar.service.EquipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EquipeController {

    private final EquipeService equipeService;

    @GetMapping
    public List<Equipe> findAll() {
        return equipeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(equipeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Equipe> save(@RequestBody Equipe equipe) {
        return ResponseEntity.ok(equipeService.save(equipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        equipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipe> update(@PathVariable Integer id, @RequestBody Equipe equipe) {
        equipe.setIdEquipe(id);
        return ResponseEntity.ok(equipeService.save(equipe));
    }
}