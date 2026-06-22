package jar.controller;

import jar.entity.Application;
import jar.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public List<Application> findAll() {
        return applicationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(applicationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Application> save(@RequestBody Application application) {
        return ResponseEntity.ok(applicationService.save(application));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> update(@PathVariable Integer id, @RequestBody Application application) {
        application.setIdApplication(id);
        return ResponseEntity.ok(applicationService.save(application));
    }
    @GetMapping("/equipe/{equipeId}")
    public List<Application> findByEquipeId(@PathVariable Integer equipeId) {
        return applicationService.findByEquipeId(equipeId);
    }

    @GetMapping("/categorie/{categorieId}")
    public List<Application> findByCategorieId(@PathVariable Integer categorieId) {
        return applicationService.findByCategorieId(categorieId);
    }

    @GetMapping("/etat/{etat}")
    public List<Application> findByEtat(@PathVariable String etat) {
        return applicationService.findByEtat(etat);
    }

    
}