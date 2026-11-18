package com.bebedero.msinfraestructura.controller;

import com.bebedero.msinfraestructura.dto.MedicionRequest;
import com.bebedero.msinfraestructura.model.LineaBebedero;
import com.bebedero.msinfraestructura.model.MedicionTemperatura;
import com.bebedero.msinfraestructura.repository.LineaBebederoRepository;
import com.bebedero.msinfraestructura.repository.MedicionTemperaturaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// La app solo lista lineas, actualiza la temperatura al hacer flushing, y
// consulta el historial de mediciones - nunca crea ni elimina lineas desde
// la interfaz.
@RestController
@RequestMapping("/api/lineas")
public class LineaBebederoController {

    private final LineaBebederoRepository lineaRepository;
    private final MedicionTemperaturaRepository medicionRepository;

    public LineaBebederoController(
            LineaBebederoRepository lineaRepository,
            MedicionTemperaturaRepository medicionRepository
    ) {
        this.lineaRepository = lineaRepository;
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

    // Actualiza la temperatura actual (el estado se recalcula solo) y deja
    // registrada una medición en el historial. La app la llama al registrar
    // un flushing, para resetear la linea a Normal.
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

    // Igual que obtenerHistorial(lineaId) en la app.
    @GetMapping("/{id}/mediciones")
    public List<MedicionTemperatura> historial(@PathVariable Long id) {
        return medicionRepository.findByLinea_IdOrderByIdDesc(id);
    }
}
