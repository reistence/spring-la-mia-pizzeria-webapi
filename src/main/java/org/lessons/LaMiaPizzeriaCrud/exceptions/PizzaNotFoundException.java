package org.lessons.LaMiaPizzeriaCrud.exceptions;

public class PizzaNotFoundException extends RuntimeException {

    public PizzaNotFoundException(String message){
        super(message);
    }
}
