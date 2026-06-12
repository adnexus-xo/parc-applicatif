package jar.controller;

import jar.entity.Historique;
import jar.service.HistoriqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiques")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HistoriqueController {

    private final HistoriqueService historiqueService;

    @GetMapping
    public List<Historique> findAll() {
        return historiqueService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Historique> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(historiqueService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Historique> save(@RequestBody Historique historique) {
        return ResponseEntity.ok(historiqueService.save(historique));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        historiqueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}