package org.lessons.LaMiaPizzeriaCrud.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

import org.lessons.LaMiaPizzeriaCrud.exceptions.PizzaNotFoundException;
import org.lessons.LaMiaPizzeriaCrud.model.Pizza;
import org.lessons.LaMiaPizzeriaCrud.model.SpecialOffer;
import org.lessons.LaMiaPizzeriaCrud.repository.PizzaRepository;
import org.lessons.LaMiaPizzeriaCrud.repository.SpecialOfferRepository;
import org.lessons.LaMiaPizzeriaCrud.service.PizzaService;
import org.lessons.LaMiaPizzeriaCrud.service.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/specialoffer")
public class SpecialOfferController {

    @Autowired
    private SpecialOfferService offerService;

    @Autowired
    private PizzaRepository pizzaRepo;

    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId") Optional<Integer> id, Model model) {
        SpecialOffer specialOffer = new SpecialOffer();
        specialOffer.setStartingDate(LocalDate.now());
        specialOffer.setEndingDate(LocalDate.now().plusMonths(1));
        if (id.isPresent()) {
            try {
               /* Pizza pizza = pizzaService.getById(id.get());*/
                Pizza pizza = pizzaRepo.findById(id.get()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
                specialOffer.setPizza(pizza);
            } catch (PizzaNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        model.addAttribute("specialoffer", specialOffer);
        return "/specialoffer/create";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute SpecialOffer formOffer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/specialoffer/create";
        }
        SpecialOffer createdOffer = offerService.create(formOffer);
        return "redirect:/pizzas/" + Integer.toString(createdOffer.getPizza().getId());
    }
}

