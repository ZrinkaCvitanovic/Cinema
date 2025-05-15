package hr.fer.kinoprojekt.application.controller;

import hr.fer.kinoprojekt.application.dto.ProjekcijaDto;
import hr.fer.kinoprojekt.application.dto.SpremiProjekcijeDto;
import hr.fer.kinoprojekt.domain.model.Projekcija;
import hr.fer.kinoprojekt.domain.service.ProjekcijaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projekcija")
@AllArgsConstructor
public class ProjekcijaController {
    private ProjekcijaService projekcijaService;

    @GetMapping("/all")
    public ResponseEntity<List<ProjekcijaDto>> getAllProjekcija() {
        final List<Projekcija> projekcije = projekcijaService.getProjekcije();
        final List<ProjekcijaDto> result = projekcije.stream().map(ProjekcijaDto::fromDomain).toList();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProjekcijaDto> getProjekcijaById(@PathVariable String id) {
        final Projekcija projekcije = projekcijaService.getProjekcijaPoId(id);
        return ResponseEntity.ok(ProjekcijaDto.fromDomain(projekcije));
    }
    @PostMapping()
    public ResponseEntity<String> save(@RequestBody SpremiProjekcijeDto projekcijaDto) {
        try {
            projekcijaService.save(projekcijaDto.toDomain(), projekcijaDto.getImeDvorana(), projekcijaDto.getUnioProjekcija(), projekcijaDto.getIdFilm());
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProjekcijaById(@PathVariable String id) {
        projekcijaService.delete(id);
        return ResponseEntity.ok("SUCCESS!");
    }
}
