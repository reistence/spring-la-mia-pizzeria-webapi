package org.lessons.LaMiaPizzeriaCrud.service;

import org.lessons.LaMiaPizzeriaCrud.exceptions.PizzaNotFoundException;
import org.lessons.LaMiaPizzeriaCrud.model.Pizza;
import org.lessons.LaMiaPizzeriaCrud.model.SpecialOffer;
import org.lessons.LaMiaPizzeriaCrud.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpecialOfferService {

    @Autowired
    private SpecialOfferRepository specialOfferRepository;
    public SpecialOffer create(SpecialOffer formOffer){
        return specialOfferRepository.save(formOffer);
    }

    public SpecialOffer getById(Integer id) throws PizzaNotFoundException {
        Optional<SpecialOffer> result = specialOfferRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException(Integer.toString(id));
        }
    }

    public SpecialOffer updateOffer(SpecialOffer offerForm, Integer id) throws PizzaNotFoundException {
        SpecialOffer offerToUpdate = getById(id);
        offerToUpdate.setTitle(offerForm.getTitle());
        offerToUpdate.setStartingDate(offerForm.getStartingDate());
        offerToUpdate.setEndingDate(offerForm.getEndingDate());
        /* pizzaToUpdate.setCover(pizzaForm.getCover());*/
        return specialOfferRepository.save(offerToUpdate);
    }

}
