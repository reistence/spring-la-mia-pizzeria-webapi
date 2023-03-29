package org.lessons.LaMiaPizzeriaCrud.controller;

import org.lessons.LaMiaPizzeriaCrud.model.Pizza;
import org.lessons.LaMiaPizzeriaCrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    public String index(Model model){
        List<Pizza> pizzas = pizzaRepo.findAll();
        model.addAttribute("menu", pizzas);
        return "/pizza/index";
    }


}
