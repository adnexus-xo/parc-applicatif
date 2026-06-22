package jar.controller;

import jar.entity.Application;
import jar.entity.DependDe;
import jar.entity.DependDeId;
import jar.service.DependDeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.time.LocalDateTime;
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
    public ResponseEntity<DependDe> save(@RequestBody Map<String, Object> body) {
    Integer idSource = (Integer) body.get("idApplicationSource");
    Integer idCible = (Integer) body.get("idApplicationCible");
    String type = (String) body.get("typeDependance");
    String description = (String) body.get("descriptionDependance");

    DependDeId id = new DependDeId();
    id.setIdApplicationSource(idSource);
    id.setIdApplicationCible(idCible);

    Application source = new Application();
    source.setIdApplication(idSource);

    Application cible = new Application();
    cible.setIdApplication(idCible);

    DependDe dependDe = new DependDe();
    dependDe.setId(id);
    dependDe.setApplicationSource(source);
    dependDe.setApplicationCible(cible);
    dependDe.setTypeDependance(type);
    dependDe.setDescriptionDependance(description);
    dependDe.setDateCreationDependance(LocalDateTime.now());

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