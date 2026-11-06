package com.bebedero.msinfraestructura.controller;

import com.bebedero.msinfraestructura.dto.LineaRequest;
import com.bebedero.msinfraestructura.dto.MedicionRequest;
import com.bebedero.msinfraestructura.model.Galpon;
import com.bebedero.msinfraestructura.model.LineaBebedero;
import com.bebedero.msinfraestructura.model.MedicionTemperatura;
import com.bebedero.msinfraestructura.repository.GalponRepository;
import com.bebedero.msinfraestructura.repository.LineaBebederoRepository;
import com.bebedero.msinfraestructura.repository.MedicionTemperaturaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lineas")
public class LineaBebederoController {

    private final LineaBebederoRepository lineaRepository;
    private final GalponRepository galponRepository;
    private final MedicionTemperaturaRepository medicionRepository;

    public LineaBebederoController(
            LineaBebederoRepository lineaRepository,
            GalponRepository galponRepository,
            MedicionTemperaturaRepository medicionRepository
    ) {
        this.lineaRepository = lineaRepository;
        this.galponRepository = galponRepository;
        this.medicionRepository = medicionRepository;
    }

    // Igual que obtenerTodasLasLineas() / obtenerLineas(galponId) en la app.
    @GetMapping
    public List<LineaBebedero> listar(@RequestParam(required = false) Long galponId) {
        if (galponId != null) {
            return lineaRepository.findByGalpon_Id(galponId);
        }
        return lineaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineaBebedero> obtener(@PathVariable Long id) {
        return lineaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LineaBebedero> crear(@RequestBody LineaRequest datos) {
        Galpon galpon = galponRepository.findById(datos.galponId()).orElse(null);
        if (galpon == null) {
            return ResponseEntity.badRequest().build();
        }
        LineaBebedero linea = new LineaBebedero(
                datos.nombre(), galpon, datos.temperaturaActual(), datos.fechaActualizacion()
        );
        return ResponseEntity.ok(lineaRepository.save(linea));
    }

    // Actualiza la temperatura actual (el estado se recalcula solo) y deja
    // registrada una medición en el historial, para igualar lo que hace
    // la fluctuacion automatica / el flushing en la app.
    @PutMapping("/{id}")
    public ResponseEntity<LineaBebedero> actualizarTemperatura(
            @PathVariable Long id, @RequestBody MedicionRequest datos
    ) {
        return lineaRepository.findById(id)
                .map(linea -> {
                    linea.setTemperaturaActual(datos.temperatura());
                    linea.setFechaActualizacion(datos.fechaHora());
                    LineaBebedero guardada = lineaRepository.save(linea);

                    medicionRepository.save(
                            new MedicionTemperatura(datos.temperatura(), datos.fechaHora(), guardada)
                    );

                    return ResponseEntity.ok(guardada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!lineaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        lineaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Igual que obtenerHistorial(lineaId) en la app.
    @GetMapping("/{id}/mediciones")
    public List<MedicionTemperatura> historial(@PathVariable Long id) {
        return medicionRepository.findByLinea_IdOrderByIdDesc(id);
    }
}
