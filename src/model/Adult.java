package src.model;

import src.database.Database;
import src.model.enums.MovieGoerAge;

public class Adult extends MovieGoer {

    private MovieGoerAge age;
    private double price;

    public Adult(String UUID, String name, String email, String mobileNum, String username, String password) {
        super(UUID, name, email, mobileNum, username, password);
        this.age = MovieGoerAge.Adult;
        this.price = Database.PRICES.getDefaultAdultPrice();
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