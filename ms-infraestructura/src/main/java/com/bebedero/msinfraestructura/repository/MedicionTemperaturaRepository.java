package com.bebedero.msinfraestructura.repository;

import com.bebedero.msinfraestructura.model.MedicionTemperatura;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicionTemperaturaRepository extends JpaRepository<MedicionTemperatura, Long> {
    List<MedicionTemperatura> findByLinea_IdOrderByIdDesc(Long lineaId);
}
