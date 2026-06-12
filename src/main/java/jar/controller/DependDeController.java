package jar.controller;

import jar.entity.DependDe;
import jar.entity.DependDeId;
import jar.service.DependDeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dependdes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DependDeController {

    private final DependDeService dependDeService;

    @GetMapping
    public List<DependDe> findAll() {
        return dependDeService.findAll();
    }

    @GetMapping("/{idSource}/{idCible}")
    public ResponseEntity<DependDe> findById(
            @PathVariable Integer idSource,
            @PathVariable Integer idCible) {
        DependDeId id = new DependDeId();
        id.setIdApplicationSource(idSource);
        id.setIdApplicationCible(idCible);
        return ResponseEntity.ok(dependDeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DependDe> save(@RequestBody DependDe dependDe) {
        return ResponseEntity.ok(dependDeService.save(dependDe));
    }

    @DeleteMapping("/{idSource}/{idCible}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer idSource,
            @PathVariable Integer idCible) {
        DependDeId id = new DependDeId();
        id.setIdApplicationSource(idSource);
        id.setIdApplicationCible(idCible);
        dependDeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}