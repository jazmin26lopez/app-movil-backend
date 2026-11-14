package com.bebedero.msinfraestructura.controller;

import com.bebedero.msinfraestructura.model.Granja;
import com.bebedero.msinfraestructura.repository.GranjaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/granjas")
public class GranjaController {

    private final GranjaRepository granjaRepository;

    public GranjaController(GranjaRepository granjaRepository) {
        this.granjaRepository = granjaRepository;
    }

    @GetMapping
    public List<Granja> listar() {
        return granjaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Granja> obtener(@PathVariable Long id) {
        return granjaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Granja crear(@RequestBody Granja granja) {
        granja.setId(null);
        return granjaRepository.save(granja);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Granja> actualizar(@PathVariable Long id, @RequestBody Granja datos) {
        return granjaRepository.findById(id)
                .map(granja -> {
                    granja.setNombre(datos.getNombre());
                    return ResponseEntity.ok(granjaRepository.save(granja));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!granjaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        granjaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
