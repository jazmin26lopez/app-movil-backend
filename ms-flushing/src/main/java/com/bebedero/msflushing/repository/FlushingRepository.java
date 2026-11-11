
package com.bebedero.msflushing.repository;

import com.bebedero.msflushing.model.Flushing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlushingRepository extends JpaRepository<Flushing, Long> {

    List<Flushing> findByLineaId(Long lineaId);

}
