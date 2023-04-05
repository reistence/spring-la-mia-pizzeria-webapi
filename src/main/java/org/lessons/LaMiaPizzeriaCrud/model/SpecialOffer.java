package org.lessons.LaMiaPizzeriaCrud.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "special_offers")
public class SpecialOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private String title;


    @ManyToOne
    private Pizza pizza;

    //GETTERS
    public Integer getId() {
        return id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public String getTitle() {
        return title;
    }

    public Pizza getPizza() {
        return pizza;
    }

    //SETTERS
    public void setId(Integer id) {
        this.id = id;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}

