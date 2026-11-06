package com.bebedero.msinfraestructura.controller;

import com.bebedero.msinfraestructura.dto.GalponRequest;
import com.bebedero.msinfraestructura.model.Galpon;
import com.bebedero.msinfraestructura.model.Granja;
import com.bebedero.msinfraestructura.repository.GalponRepository;
import com.bebedero.msinfraestructura.repository.GranjaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/galpones")
public class GalponController {

    private final GalponRepository galponRepository;
    private final GranjaRepository granjaRepository;

    public GalponController(GalponRepository galponRepository, GranjaRepository granjaRepository) {
        this.galponRepository = galponRepository;
        this.granjaRepository = granjaRepository;
    }

    @GetMapping
    public List<Galpon> listar(@RequestParam(required = false) Long granjaId) {
        if (granjaId != null) {
            return galponRepository.findByGranja_Id(granjaId);
        }
        return galponRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Galpon> obtener(@PathVariable Long id) {
        return galponRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Galpon> crear(@RequestBody GalponRequest datos) {
        Granja granja = granjaRepository.findById(datos.granjaId())
                .orElse(null);
        if (granja == null) {
            return ResponseEntity.badRequest().build();
        }
        Galpon galpon = new Galpon(datos.nombre(), granja);
        return ResponseEntity.ok(galponRepository.save(galpon));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Galpon> actualizar(@PathVariable Long id, @RequestBody GalponRequest datos) {
        return galponRepository.findById(id)
                .map(galpon -> {
                    galpon.setNombre(datos.nombre());
                    return ResponseEntity.ok(galponRepository.save(galpon));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!galponRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        galponRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
