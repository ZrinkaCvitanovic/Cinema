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
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/projekcija")
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

    @GetMapping("/dvorana/{imeDvorane}")
    public ResponseEntity<List<ProjekcijaDto>> getByImeDvorane(@PathVariable String imeDvorane) {
        final List<Projekcija> projekcije = projekcijaService.filterByDvorana(imeDvorane);
        final List<ProjekcijaDto> result = projekcije.stream().map(ProjekcijaDto::fromDomain).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/film/{idFilm}")
    public ResponseEntity<List<ProjekcijaDto>> getByIdFilm(@PathVariable Integer idFilm) {
        final List<Projekcija> projekcije = projekcijaService.filterByFilm(idFilm);
        final List<ProjekcijaDto> result = projekcije.stream().map(ProjekcijaDto::fromDomain).toList();
        return ResponseEntity.ok(result);
    }


    @PostMapping()
    public ResponseEntity<String> create(@RequestBody SpremiProjekcijeDto projekcijaDto) {
        try {
            boolean legit = projekcijaService.checkAvailability(projekcijaDto);
            if (!legit) {
                return ResponseEntity.badRequest().body("Postoji vremensko preklapanje za ovaj film, odaberite novo vrijeme");
            }
            projekcijaService.save(projekcijaDto.toDomain(), projekcijaDto.getImeDvorana(), projekcijaDto.getIdFilm(), projekcijaDto.getIdTip());
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<String> update(@RequestBody SpremiProjekcijeDto projekcijaDto) {
        try {
            projekcijaService.save(projekcijaDto.toDomain(), projekcijaDto.getImeDvorana(), projekcijaDto.getIdFilm(), projekcijaDto.getIdTip());
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
