package hr.fer.kinoprojekt.application.controller;

import hr.fer.kinoprojekt.domain.model.Redatelj;
import hr.fer.kinoprojekt.domain.service.RedateljService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/redatelj")
public class RedateljController {

    private RedateljService service;

    @GetMapping("/all")
    public ResponseEntity<List<Redatelj>> getRedatelji() {
        final List<Redatelj> redatelji = service.getRedatelji();
        return ResponseEntity.ok(redatelji);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Redatelj> getRedatelj(@PathVariable Integer id) {
        final Redatelj redatelj = service.getRedatelj(id);
        return ResponseEntity.ok(redatelj);
    }

    @PostMapping(consumes = {"*/*"})
    public ResponseEntity<String> save(@RequestBody Redatelj redatelj) {
        try {
            service.save(redatelj);
            return ResponseEntity.ok("SUCCESS!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilmById(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("SUCCESS!");
    }

}
