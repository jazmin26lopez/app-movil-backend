package com.bebedero.msinfraestructura.repository;

import com.bebedero.msinfraestructura.model.LineaBebedero;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LineaBebederoRepository extends JpaRepository<LineaBebedero, Long> {
    List<LineaBebedero> findByGalpon_Id(Long galponId);
}
