package org.lessons.LaMiaPizzeriaCrud.controller;

import jakarta.validation.Valid;
import org.lessons.LaMiaPizzeriaCrud.exceptions.PizzaNotFoundException;
import org.lessons.LaMiaPizzeriaCrud.model.Ingredient;
import org.lessons.LaMiaPizzeriaCrud.model.Pizza;
import org.lessons.LaMiaPizzeriaCrud.repository.PizzaRepository;
import org.lessons.LaMiaPizzeriaCrud.service.IngredientService;
import org.lessons.LaMiaPizzeriaCrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredientService ingredientService;

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
        List<Ingredient> ingredient = ingredientService.getAll();
        model.addAttribute("ingredientList", ingredient );
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


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        try {
            Pizza pizza = pizzaService.getById(id);
            List<Ingredient> ingredient = ingredientService.getAll();
            model.addAttribute("pizza", pizza);
            model.addAttribute("ingredientList", ingredient );

            return "/pizza/edit";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza id: " + id + " not found");
        }
    }

   @PostMapping("/edit/{id}")
   public String editPizza(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
       try {
           Pizza updatePizza = pizzaService.updatePizza(formPizza, id);
           return "redirect:/pizzas/{id}";
       } catch (RuntimeException e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + id + " not found");
       }
   }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            Pizza pizza = pizzaService.getById(id);
            redirectAttributes.addFlashAttribute("danger", "La pizza " + pizza.getName() + " è stata cancellata con successo");
            pizzaService.deleteById(id);
            return "/pizzas/index";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza id: " + id + " not found");
        }
    }


}
