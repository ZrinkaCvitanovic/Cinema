package hr.fer.kinoprojekt.application.controller;

import hr.fer.kinoprojekt.domain.model.Administrator;
import hr.fer.kinoprojekt.domain.service.AdministratorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdministratorController {

    private AdministratorService service;

    @GetMapping("/all")
    public ResponseEntity<List<Administrator>> getAllAdministrator() {
        final List<Administrator> administrators = service.getAdministrators();
        return ResponseEntity.ok(administrators);
    }

}
