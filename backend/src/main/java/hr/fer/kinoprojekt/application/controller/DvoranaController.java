package hr.fer.kinoprojekt.application.controller;

import hr.fer.kinoprojekt.application.dto.DvoranaDto;
import hr.fer.kinoprojekt.domain.service.DvoranaService;
import hr.fer.kinoprojekt.domain.model.Dvorana;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/dvorana")
@CrossOrigin(origins = {"http://localhost:3000"})
public class DvoranaController {

    private DvoranaService service;

    @GetMapping("/all")
    public ResponseEntity<List<DvoranaDto>> getAllDvorana() {
        final List<Dvorana> dvorane = service.getDvorane();
        final List<DvoranaDto> result = dvorane.stream().map(DvoranaDto::fromDomain).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{ime}")
    public ResponseEntity<DvoranaDto> getDvorana(@PathVariable String ime) {
        try {
            final Dvorana dvorana = service.getDvorana(ime);
            return ResponseEntity.ok(DvoranaDto.fromDomain(dvorana));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = {"*/*"})
    public ResponseEntity<String> save(@RequestBody DvoranaDto dto) {
        try {
            service.save(dto.toDomain(), dto.getUnioZaposlenik());
            return ResponseEntity.ok("SUCCESS!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{ime}")
    public ResponseEntity<String> deleteDvoranaByIme(@PathVariable String ime) {
        service.delete(ime);
        return ResponseEntity.ok("SUCCESS!");
    }
}
