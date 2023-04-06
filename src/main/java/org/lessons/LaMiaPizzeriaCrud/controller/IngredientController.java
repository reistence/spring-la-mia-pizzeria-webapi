package org.lessons.LaMiaPizzeriaCrud.controller;


import jakarta.persistence.Column;
import jakarta.validation.Valid;
import org.lessons.LaMiaPizzeriaCrud.exceptions.PizzaNotFoundException;
import org.lessons.LaMiaPizzeriaCrud.model.Ingredient;
import org.lessons.LaMiaPizzeriaCrud.model.Pizza;
import org.lessons.LaMiaPizzeriaCrud.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("list", ingredientService.getAll());
        model.addAttribute("ingredientObj", new Ingredient());
        return "/ingredients/index";
    }


    @PostMapping("/save")
    public String doSave(@Valid @ModelAttribute(name = "ingredientsObj") Ingredient ingredient, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("list", ingredientService.getAll());
            return "/ingredients/index";
        }

        ingredientService.create(ingredient);
        return "redirect:/ingredients";

    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        try {
            Ingredient ingredient = ingredientService.getById(id);
            model.addAttribute("ingredient", ingredient );

            return "/ingredients/edit";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente con id: " + id + " not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String editPizza(@PathVariable Integer id, @Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult) {
        try {
            Ingredient updateIngredient = ingredientService.update(formIngredient, id);
            return "redirect:/ingredients";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente con id " + id + " non trovato");
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            Ingredient ingredient = ingredientService.getById(id);
            redirectAttributes.addFlashAttribute("danger", "L'Ingrediente " + ingredient.getName() + " è stato cancellato con successo");
            ingredientService.deleteById(id);
            return "redirect:/ingredients";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza id: " + id + " not found");
        }
    }


}
