package org.lessons.LaMiaPizzeriaCrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank
    @Size(min = 5, max = 255, message = "Il Nome della pizza non può superare i 255 caratteri")
    @Column(nullable = false)
    private String name;



    @NotBlank
    @Size(min = 5, max = 20000, message = "La lista degli ingredienti non può superare i 2000 caratteri")
    @Column(nullable = false)
    private String description;


    @NotBlank
    @Size(min = 5, max = 20000, message = "Il link dell'immagine non può superare i 2000 caratteri")
    private String cover;

    @Positive
    @Column(nullable = false)
    private float price;


    @JsonIgnore
    @OneToMany(mappedBy = "pizza")
    private List<SpecialOffer> specialOffers;







    @ManyToMany
    @JoinTable(
            name = "pizza_ingredient",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients;

    //Constructors
    public Pizza() {
        super();
    }

    public Pizza(Integer id, String name, String description, String cover, float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cover = cover;
        this.price = price;
    }

    //G/Setters
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public List<Ingredient> getIngredients(){
        return ingredients;
    }

    public List<SpecialOffer> getSpecialOffers(){
        return specialOffers;
    }

    public void setSpecialOffers(List<SpecialOffer> offers){
         this.specialOffers = offers;
    }



}
