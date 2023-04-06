package org.lessons.LaMiaPizzeriaCrud.service;

import org.lessons.LaMiaPizzeriaCrud.exceptions.PizzaNotFoundException;
import org.lessons.LaMiaPizzeriaCrud.model.Ingredient;
import org.lessons.LaMiaPizzeriaCrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepo;

    public List<Ingredient> getAll(){
        return ingredientRepo.findAll(Sort.by("name"));
    }

    public Ingredient create(Ingredient formIngredients){
        Ingredient ingredientToCreate = new Ingredient();
        ingredientToCreate.setName(formIngredients.getName());
        return ingredientRepo.save(ingredientToCreate);
    }

    public Ingredient getById(Integer id) throws PizzaNotFoundException {
        Optional<Ingredient> result = ingredientRepo.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException(Integer.toString(id));
        }
    }


    public Ingredient update(Ingredient formIngredients, Integer id){
        Ingredient ingredientToCreate = new Ingredient();
        ingredientToCreate.setName(formIngredients.getName());
        return ingredientRepo.save(ingredientToCreate);
    }

    public boolean deleteById(Integer id) {
        ingredientRepo.findById(id).orElseThrow(() -> new PizzaNotFoundException(Integer.toString(id)));
        try {
            ingredientRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
