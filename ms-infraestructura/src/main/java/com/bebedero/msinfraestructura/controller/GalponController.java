package com.bebedero.msinfraestructura.controller;

import com.bebedero.msinfraestructura.model.Galpon;
import com.bebedero.msinfraestructura.repository.GalponRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Solo lista: la app consulta galpones, nunca los crea/edita/elimina desde la interfaz.
@RestController
@RequestMapping("/api/galpones")
public class GalponController {

    private final GalponRepository galponRepository;

    public GalponController(GalponRepository galponRepository) {
        this.galponRepository = galponRepository;
    }

    @GetMapping
    public List<Galpon> listar(@RequestParam(required = false) Long granjaId) {
        if (granjaId != null) {
            return galponRepository.findByGranja_Id(granjaId);
        }
        return galponRepository.findAll();
    }
}
