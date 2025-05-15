package hr.fer.kinoprojekt.application.controller;

import hr.fer.kinoprojekt.application.dto.FilmDto;
import hr.fer.kinoprojekt.application.dto.SpremiFilmDto;
import hr.fer.kinoprojekt.domain.model.Film;
import hr.fer.kinoprojekt.domain.service.FilmService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/film")
public class FilmController {

    private FilmService service;

    @GetMapping("/all")
    public ResponseEntity<List<FilmDto>> getAllFilms() {
        final List<Film> filmovi = service.getFilmovi();
        final List<FilmDto> result = filmovi.stream().map(FilmDto::fromDomain).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> getFilmById(@PathVariable String id) {
        final Film film = service.getFilm(id);
        return ResponseEntity.ok(FilmDto.fromDomain(film));
    }

    @PostMapping(value = "", consumes = {"application/json"})
    public ResponseEntity<String> save(@RequestBody SpremiFilmDto dto) {
        try {
            service.save(dto.toDomain(), dto.getUnioZaposlenik());
            return ResponseEntity.ok("SUCCESS!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilmById(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok("SUCCESS!");
    }
}

