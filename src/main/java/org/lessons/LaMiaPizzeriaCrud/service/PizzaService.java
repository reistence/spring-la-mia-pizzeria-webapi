package org.lessons.LaMiaPizzeriaCrud.service;

import org.lessons.LaMiaPizzeriaCrud.exceptions.PizzaNotFoundException;
import org.lessons.LaMiaPizzeriaCrud.model.Pizza;
import org.lessons.LaMiaPizzeriaCrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepo;

    public Pizza createPizza(Pizza formPizza){
        Pizza pizzaToSave = new Pizza();
        pizzaToSave.setName(formPizza.getName());
        pizzaToSave.setDescription(formPizza.getDescription());
        pizzaToSave.setCover(formPizza.getCover());
        pizzaToSave.setPrice(formPizza.getPrice());
        pizzaToSave.setIngredients(formPizza.getIngredients());
        return pizzaRepo.save(pizzaToSave);
    }

    public List<Pizza> getAllPizzas(){
        return pizzaRepo.findAll(Sort.by("name"));
    }



    public List<Pizza> getFilteredPizzas(String keyword) {
        return pizzaRepo.findByNameContainingIgnoreCase(keyword);
    }

    public Pizza getById(Integer id) throws PizzaNotFoundException {
        Optional<Pizza> result = pizzaRepo.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException(Integer.toString(id));
        }
    }

    public Pizza updatePizza(Pizza pizzaForm, Integer id) throws PizzaNotFoundException{
        Pizza pizzaToUpdate = getById(id);
        pizzaToUpdate.setName(pizzaForm.getName());
        pizzaToUpdate.setPrice(pizzaForm.getPrice());
        pizzaToUpdate.setDescription(pizzaForm.getDescription());
        pizzaToUpdate.setCover(pizzaForm.getCover());
        pizzaToUpdate.setIngredients(pizzaForm.getIngredients());
        return pizzaRepo.save(pizzaToUpdate);
    }

    public boolean deleteById(Integer id) {
        pizzaRepo.findById(id).orElseThrow(() -> new PizzaNotFoundException(Integer.toString(id)));
        try {
            pizzaRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
