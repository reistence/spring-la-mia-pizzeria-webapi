package org.lessons.LaMiaPizzeriaCrud.controller;

import jakarta.validation.Valid;
import org.lessons.LaMiaPizzeriaCrud.exceptions.PizzaNotFoundException;
import org.lessons.LaMiaPizzeriaCrud.model.Pizza;
import org.lessons.LaMiaPizzeriaCrud.repository.PizzaRepository;
import org.lessons.LaMiaPizzeriaCrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/home")
    public String home(Model model){
        return "/pizza/home";
    }

    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "q") Optional<String> keyword){
        List<Pizza> pizzas;
        if (keyword.isEmpty()) pizzas = pizzaService.getAllPizzas();
        else {
            pizzas = pizzaService.getFilteredPizzas(keyword.get());
            model.addAttribute("keyword", keyword.get());
        }
        model.addAttribute("menu", pizzas);
        return "/pizza/index";
    }


    @GetMapping("/{pizzaId}")
    public String show(@PathVariable("pizzaId") Integer id, Model model){
     /*   Optional<Pizza> result = pizzaRepo.findById(id);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "/pizza/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza id: " + id + " not found");
        }*/
        try {
            Pizza pizza = pizzaService.getById(id);
            model.addAttribute("pizza", pizza);
            return "/pizza/show";
        } catch (PizzaNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza id: " + id + " not found");
        }

    }


    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("pizza", new Pizza());
        return "/pizza/create";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model){
        //Validation
        if (bindingResult.hasErrors()){
            return "/pizza/create";
        }
        pizzaService.createPizza(formPizza);
        return "redirect:/pizzas/index";
    }


}
