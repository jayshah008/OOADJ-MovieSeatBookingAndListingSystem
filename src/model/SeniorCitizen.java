package src.model;

import src.database.*;
import src.model.enums.*;

public class SeniorCitizen extends MovieGoer {

    private MovieGoerAge age;

    private double price;

    public SeniorCitizen(String UUID, String name, String email, String mobileNum, String username, String password) {
        super(UUID, name, email, mobileNum, username, password);
        this.age = MovieGoerAge.SeniorCitizen;
        this.price = Database.PRICES.getDefaultSeniorCitizenPrice();
    }

    public MovieGoerAge getMovieGoerAge() {
        return this.age;
    }

    public void setGoerPrice(double price) {
        this.price = price;
    }

    public double getGoerPrice() {
        return this.price;
    }
}