package src.model;

import java.io.Serializable;
import java.util.*;
import src.model.enums.*;

public abstract class MovieGoer implements Serializable {

    private String name;

    private String UUID;

    private String email;

    private String mobileNum;

    private String username;

    private String password;

    private ArrayList<BookingHistory> bookingHistory;

    private ArrayList<MovieReview> reviewHistory;

    private final static long serialVersionUID = 9L;

    public MovieGoer(String UUID, String name, String email, String mobileNum, String username, String password) {
        this.name = name;
        this.UUID = UUID;
        this.email = email;
        this.mobileNum = mobileNum;
        this.username = username;
        this.password = password;
        this.bookingHistory = new ArrayList<>();
        this.reviewHistory = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUUID() {
        return this.UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public ArrayList<BookingHistory> getBookingHistory() {
        return this.bookingHistory;
    }

    public void setBookingHistory(ArrayList<BookingHistory> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public void addBookingHistory(BookingHistory history) {
        this.bookingHistory.add(history);
    }

    public ArrayList<MovieReview> getReviewHistory() {
        return reviewHistory;
    }

    public void setReviewHistory(ArrayList<MovieReview> reviewHistory) {
        this.reviewHistory = reviewHistory;
    }

    public void addReviewHistory(MovieReview movieReview) {
        this.reviewHistory.add(movieReview);
    }

    public abstract MovieGoerAge getMovieGoerAge();

    public abstract double getGoerPrice();

    public abstract void setGoerPrice(double price);
}