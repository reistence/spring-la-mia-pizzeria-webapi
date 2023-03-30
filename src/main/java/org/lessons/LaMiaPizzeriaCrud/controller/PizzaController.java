package org.lessons.LaMiaPizzeriaCrud.controller;

import org.lessons.LaMiaPizzeriaCrud.model.Pizza;
import org.lessons.LaMiaPizzeriaCrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepo;

    @GetMapping("/home")
    public String home(Model model){
        return "/pizza/home";
    }

    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "q") Optional<String> keyword){
        List<Pizza> pizzas;
        if (keyword.isEmpty()) pizzas = pizzaRepo.findAll(Sort.by("name"));
        else {
            pizzas = pizzaRepo.findByNameContainingIgnoreCase(keyword.get());
            model.addAttribute("keyword", keyword.get());
        }
        model.addAttribute("menu", pizzas);
        return "/pizza/index";
    }


    @GetMapping("/{pizzaId}")
    public String show(@PathVariable("pizzaId") Integer id, Model model){
        Optional<Pizza> result = pizzaRepo.findById(id);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "/pizza/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza id: " + id + " not found");
        }

    }


}
