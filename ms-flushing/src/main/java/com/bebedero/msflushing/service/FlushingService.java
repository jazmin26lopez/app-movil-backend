
package com.bebedero.msflushing.service;

import com.bebedero.msflushing.dto.FlushingRequest;
import com.bebedero.msflushing.model.Flushing;
import com.bebedero.msflushing.repository.FlushingRepository;
import org.springframework.stereotype.Service;
import com.bebedero.msflushing.dto.FlushingUpdateRequest;


import java.util.List;
import java.util.Optional;

@Service
public class FlushingService {

    private final FlushingRepository repository;

    public FlushingService(FlushingRepository repository) {
        this.repository = repository;
    }

    // Consultar todos los registros o filtrar por línea
    public List<Flushing> listar(Long lineaId) {
        if (lineaId != null) {
            return repository.findByLineaId(lineaId);
        }

        return repository.findAll();
    }

    // Buscar un flushing por ID
    public Optional<Flushing> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Crear un nuevo flushing
    public Flushing crear(FlushingRequest request) {

        Flushing flushing = new Flushing(
                request.getLineaId(),
                request.getUsuarioId(),
                request.getFechaHora(),
                request.getObservacion(),
                request.getFotoUri()
        );

        return repository.save(flushing);
    }

    // Actualizar la observación de un flushing
    public Optional<Flushing> actualizar(
            Long id, FlushingUpdateRequest request) {

        return repository.findById(id)
                .map(flushing -> {
                    flushing.setObservacion(request.getObservacion());
                    return repository.save(flushing);
                });
    }

    // Eliminar un flushing
    public boolean eliminar(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}
