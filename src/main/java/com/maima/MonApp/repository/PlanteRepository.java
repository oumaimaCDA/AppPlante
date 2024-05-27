package com.maima.MonApp.repository;

import com.maima.MonApp.model.Plante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanteRepository extends JpaRepository<Plante ,Long> {

 @Query("SELECT p FROM Plante p WHERE lower(p.name) LIKE lower(concat('%',:query,'%'))" +
        "OR lower(p.planteType) LIKE lower(concat('%',:query,'%'))")
    List<Plante> findBySearchQuery(String query);

    Plante findByCustomerId(Long userId);
}
