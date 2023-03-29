package org.lessons.LaMiaPizzeriaCrud.repository;

import org.lessons.LaMiaPizzeriaCrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
}
