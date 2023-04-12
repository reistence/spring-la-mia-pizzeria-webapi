package org.lessons.LaMiaPizzeriaCrud.api;

import jakarta.validation.Valid;
import org.lessons.LaMiaPizzeriaCrud.exceptions.PizzaNotFoundException;
import org.lessons.LaMiaPizzeriaCrud.model.Pizza;
import org.lessons.LaMiaPizzeriaCrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/pizzas")
public class PizzaRestController {


    @Autowired
    private PizzaService pizzaService;



    //All pizzas
    @GetMapping
    public List<Pizza> list(@RequestParam(name="q") Optional<String> search){
        if (search.isPresent()){
            return pizzaService.getFilteredPizzas(search.get());
        }
        return pizzaService.getAllPizzas();
    }


    //Single pizza
    @GetMapping("/{id}")
    public Pizza getById(@PathVariable Integer id){
        try {
            return pizzaService.getById(id);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    //create Pizza
    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza){
        return pizzaService.createPizza(pizza);
    }


    //update Pizza
    @PutMapping("/{id}")
    public Pizza update(@PathVariable Integer id, @Valid @RequestBody Pizza pizza){
       try {
           return pizzaService.updatePizza(pizza, id);
       } catch (PizzaNotFoundException e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       } catch (Exception e){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
       }
    }


    //delete pizza
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        try {
            boolean success = pizzaService.deleteById(id);
        } catch (PizzaNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }





}
