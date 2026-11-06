package com.bebedero.msinfraestructura.repository;

import com.bebedero.msinfraestructura.model.Galpon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GalponRepository extends JpaRepository<Galpon, Long> {
    List<Galpon> findByGranja_Id(Long granjaId);
}
