
package com.bebedero.msflushing.controller;

import com.bebedero.msflushing.dto.FlushingRequest;
import com.bebedero.msflushing.model.Flushing;
import com.bebedero.msflushing.service.FlushingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bebedero.msflushing.dto.FlushingUpdateRequest;

import java.util.List;

@Tag(
        name = "Gestión de Flushing",
        description = "Operaciones para administrar los registros de limpieza de bebederos"
)
@RestController
@RequestMapping("/api/flushings")
public class FlushingController {

    private final FlushingService service;

    public FlushingController(FlushingService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los flushings o filtrar por línea")
    @GetMapping
    public List<Flushing> listar(
            @RequestParam(required = false) Long lineaId) {

        return service.listar(lineaId);
    }

    @Operation(summary = "Consultar un flushing por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Flushing> buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar un nuevo flushing")
    @PostMapping
    public ResponseEntity<Flushing> crear(
            @Valid @RequestBody FlushingRequest request) {

        Flushing guardado = service.crear(request);

        return ResponseEntity.status(201).body(guardado);
    }


    @Operation(summary = "Actualizar la observación de un flushing")
    @PutMapping("/{id}")
    public ResponseEntity<Flushing> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody FlushingUpdateRequest request) {

        return service.actualizar(id,request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Eliminar un flushing")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        if (!service.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
