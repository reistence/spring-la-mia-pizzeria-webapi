package org.lessons.LaMiaPizzeriaCrud.service;

import org.lessons.LaMiaPizzeriaCrud.model.SpecialOffer;
import org.lessons.LaMiaPizzeriaCrud.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialOfferService {

    @Autowired
    private SpecialOfferRepository specialOfferRepository;
    public SpecialOffer create(SpecialOffer formOffer){
        return specialOfferRepository.save(formOffer);
    }

}
