package graduation.model;

import java.time.LocalDate;

public class Menu extends AbstractBaseEntity {

    // TODO: 06.04.2020 add DB and jpa annotations etc.
    public static final String DELETE = "Menu.delete";
    public static final String ALL_SORTED = "Menu.getAllSorted";

    private String meal;
    private Double price;
    private LocalDate date;

    public Menu(){

    }

    public Menu(String meal, Double price){
        this.meal = meal;
        this.price = price;
        this.date = LocalDate.now();
    }

    public Menu(String meal, Double price, LocalDate date){
        this.meal = meal;
        this.price = price;
        this.date = date;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
