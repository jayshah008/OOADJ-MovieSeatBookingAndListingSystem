package src.model;

import src.database.*;
import src.model.enums.*;

public class Student extends MovieGoer {

    private MovieGoerAge age;

    private double price;

    public Student(String UUID, String name, String email, String mobileNum, String username, String password) {
        super(UUID, name, email, mobileNum, username, password);
        this.age = MovieGoerAge.Student;
        this.price = Database.PRICES.getDefaultStudentPrice();
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