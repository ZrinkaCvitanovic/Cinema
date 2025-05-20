package hr.fer.kinoprojekt.application.controller;

import hr.fer.kinoprojekt.application.dto.FilmDto;
import hr.fer.kinoprojekt.application.dto.ProjekcijaDto;
import hr.fer.kinoprojekt.application.dto.SpremiFilmDto;
import hr.fer.kinoprojekt.domain.model.Film;
import hr.fer.kinoprojekt.domain.model.Projekcija;
import hr.fer.kinoprojekt.domain.service.FilmService;
import hr.fer.kinoprojekt.domain.service.ProjekcijaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/film")
public class FilmController {

    private FilmService service;
    private ProjekcijaService projekcijaService;

    @GetMapping("/all")
    public ResponseEntity<List<FilmDto>> getAllFilms() {
        final List<Film> filmovi = service.getFilmovi();
        final List<FilmDto> result = filmovi.stream().map(FilmDto::fromDomain).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> getFilmById(@PathVariable Integer id) {
        final Film film = service.getFilm(id);
        List<Projekcija> projekcijaList = projekcijaService.filterByFilm(id);
        List<ProjekcijaDto> projekcijaDtos = projekcijaList.stream().map(ProjekcijaDto::fromDomain).toList();
        FilmDto filmdto = FilmDto.fromDomain(film);
        filmdto.setProjekcijaDtoList(projekcijaDtos);
        return ResponseEntity.ok(filmdto);
    }

    @PostMapping(consumes = {"*/*"})
    public ResponseEntity<String> save(@RequestBody SpremiFilmDto dto) {
        try {
            service.save(dto.toDomain(), dto.getIdRedatelj());
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

