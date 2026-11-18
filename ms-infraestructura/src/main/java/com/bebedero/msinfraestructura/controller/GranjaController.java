package com.bebedero.msinfraestructura.controller;

import com.bebedero.msinfraestructura.model.Granja;
import com.bebedero.msinfraestructura.repository.GranjaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Solo lista: la app consulta granjas, nunca las crea/edita/elimina desde la interfaz.
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
}
