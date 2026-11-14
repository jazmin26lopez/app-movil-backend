
package com.bebedero.msflushing.controller;

import com.bebedero.msflushing.dto.FlushingRequest;
import com.bebedero.msflushing.model.Flushing;
import com.bebedero.msflushing.repository.FlushingRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@Tag(
        name = "Gestión de Flushing",
        description = "Operaciones para administrar los registros de limpieza de bebederos"
)

@RestController
@RequestMapping("/api/flushings")
public class FlushingController {

    private final FlushingRepository repository;

    public FlushingController(FlushingRepository repository) {
        this.repository = repository;
    }

    // GET: consultar todos o filtrar por línea
    @Operation(summary = "Consultar un flushing por ID")
    @GetMapping
    public List<Flushing> listar(
            @RequestParam(required = false) Long lineaId) {

        if (lineaId != null) {
            return repository.findByLineaId(lineaId);
        }

        return repository.findAll();
    }

    // GET: buscar un flushing por ID
    @GetMapping("/{id}")
    public ResponseEntity<Flushing> buscarPorId(
            @PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: registrar un flushing
    @Operation(summary = "Registrar un nuevo flushing")
    @PostMapping
    public ResponseEntity<Flushing> crear(
            @Valid @RequestBody FlushingRequest request) {

        Flushing flushing = new Flushing(
                request.getLineaId(),
                request.getUsuarioId(),
                request.getFechaHora(),
                request.getObservacion(),
                request.getFotoUri()
        );

        Flushing guardado = repository.save(flushing);

        return ResponseEntity.status(201).body(guardado);
    }

    // PUT: actualizar la observación
    @Operation(summary = "Actualizar la observación de un flushing")
    @PutMapping("/{id}")
    public ResponseEntity<Flushing> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody FlushingRequest request) {

        return repository.findById(id)
                .map(flushing -> {
                    flushing.setObservacion(request.getObservacion());
                    Flushing actualizado = repository.save(flushing);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE: eliminar un flushing
    @Operation(summary = "Eliminar un flushing")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
