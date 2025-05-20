package hr.fer.kinoprojekt.application.controller;

import hr.fer.kinoprojekt.domain.model.TipProjekcije;
import hr.fer.kinoprojekt.domain.service.TipProjekcijeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/tip")
public class TipProjekcijeController {

    private TipProjekcijeService service;

    @GetMapping("/all")
    public ResponseEntity<List<TipProjekcije>> getTipoviProjekcije() {
        final List<TipProjekcije> tipovi = service.getTipoviProjekcije();
        return ResponseEntity.ok(tipovi);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipProjekcije> getTipProjekcije(@PathVariable Integer id) {
        final TipProjekcije tip = service.getTipProjekcije(id);
        return ResponseEntity.ok(tip);
    }

    @PostMapping(consumes = {"*/*"})
    public ResponseEntity<String> save(@RequestBody TipProjekcije tip) {
        try {
            service.save(tip);
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
