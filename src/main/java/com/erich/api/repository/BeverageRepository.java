package com.erich.api.repository;

import com.erich.api.model.Beverage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BeverageRepository extends CrudRepository<Beverage, Long> {

    Iterable<Beverage> findAllBySection(Integer section);

    Iterable<Beverage> findAllByOrderByTimestampAsc();

    List<Beverage> findAllByAlcoholicAndSection(boolean isAlcoholic, Integer section);

    @Query("select b.volumeInLiters from Beverage b where b.section = :section")
    List<Integer> getAllVolumenInLittersBySection(@Param("section") Integer section);

}
