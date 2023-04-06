package org.lessons.LaMiaPizzeriaCrud.repository;

import org.lessons.LaMiaPizzeriaCrud.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {


}
