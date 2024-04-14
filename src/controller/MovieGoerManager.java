package src.controller;

import java.util.*;
import src.database.*;
import src.model.*;
import src.handler.*;

public class MovieGoerManager {


    public MovieGoerManager() {
    }


    public static MovieGoer createGoerAdult(String name, String email, String mobileNum, String username,
            String password) {
        String UUID = String.format("MG%04d", DatabaseHandler.generateUUID(Database.MOVIE_GOER));
        MovieGoer goer = new Adult(UUID, name, email, mobileNum, username, password);
        DatabaseManager.saveUpdateToDatabase(UUID, goer, Database.MOVIE_GOER);
        return goer;
    }


    public static MovieGoer createGoerChild(String name, String email, String mobileNum, String username,
            String password) {
        String UUID = String.format("MG%04d", DatabaseHandler.generateUUID(Database.MOVIE_GOER));
        MovieGoer goer = new Child(UUID, name, email, mobileNum, username, password);
        DatabaseManager.saveUpdateToDatabase(UUID, goer, Database.MOVIE_GOER);
        return goer;
    }


    public static MovieGoer createGoerSeniorCitizen(String name, String email, String mobileNum, String username,
            String password) {
        String UUID = String.format("MG%04d", DatabaseHandler.generateUUID(Database.MOVIE_GOER));
        MovieGoer goer = new SeniorCitizen(UUID, name, email, mobileNum, username, password);
        DatabaseManager.saveUpdateToDatabase(UUID, goer, Database.MOVIE_GOER);
        return goer;
    }


    public static MovieGoer createGoerStudent(String name, String email, String mobileNum, String username,
            String password) {
        String UUID = String.format("MG%04d", DatabaseHandler.generateUUID(Database.MOVIE_GOER));
        MovieGoer goer = new Student(UUID, name, email, mobileNum, username, password);
        DatabaseManager.saveUpdateToDatabase(UUID, goer, Database.MOVIE_GOER);
        return goer;
    }


    public static ArrayList<Movie> rankTop5(String choice, ArrayList<Movie> movies, boolean staff) {
        if (!staff) {
            if (choice == "ticket" && !Database.PERMISSION.getMovieSalesPermission()) {
                return null;
            }
            if (choice == "ratings" && !Database.PERMISSION.getOverallRatingsPermission()) {
                return null;
            }
        }
        if (movies.size() <= 1) {
            return movies;
        }
        return MovieManager.sortMovie(movies, choice);
    }


    public static void addBookingHistory(BookingHistory history, MovieGoer goer) {
        goer.addBookingHistory(history);
    }
}
