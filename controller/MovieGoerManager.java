package src.controller;

import java.util.*;
import src.database.*;
import src.model.*;
import src.handler.*;

/**
 * Controller class for handling all logic related to {@link MovieGoer}
 * 
 * @author Jerick, Yeek Sheng
 * @version 1.0
 */
public class MovieGoerManager {

    /**
     * Constructor
     */
    public MovieGoerManager() {
    }

    /**
     * Method to create a {@link Adult} instance and save to database
     * 
     * @param name      is the name of the Movie Goer
     * @param email     is the email address of the Movie Goer
     * @param mobileNum is the mobile number of the Movie Goer
     * @param username  is the username of Movie Goer in MOBLIMA
     * @param password  is the password of Movie Goer in MOBLIMA
     * @return The created {@link MovieGoer} object
     */
    public static MovieGoer createGoerAdult(String name, String email, String mobileNum, String username,
            String password) {
        String UUID = String.format("MG%04d", DatabaseHandler.generateUUID(Database.MOVIE_GOER));
        MovieGoer goer = new Adult(UUID, name, email, mobileNum, username, password);
        DatabaseManager.saveUpdateToDatabase(UUID, goer, Database.MOVIE_GOER);
        return goer;
    }

    /**
     * Method to create a {@link Child} instance and save to database
     * 
     * @param name      is the name of the Movie Goer
     * @param email     is the email address of the Movie Goer
     * @param mobileNum is the mobile number of the Movie Goer
     * @param username  is the username of Movie Goer in MOBLIMA
     * @param password  is the password of Movie Goer in MOBLIMA
     * @return The created {@link MovieGoer} object
     */
    public static MovieGoer createGoerChild(String name, String email, String mobileNum, String username,
            String password) {
        String UUID = String.format("MG%04d", DatabaseHandler.generateUUID(Database.MOVIE_GOER));
        MovieGoer goer = new Child(UUID, name, email, mobileNum, username, password);
        DatabaseManager.saveUpdateToDatabase(UUID, goer, Database.MOVIE_GOER);
        return goer;
    }

    /**
     * Method to create a {@link SeniorCitizen} instance and save to database
     * 
     * @param name      is the name of the Movie Goer
     * @param email     is the email address of the Movie Goer
     * @param mobileNum is the mobile number of the Movie Goer
     * @param username  is the username of Movie Goer in MOBLIMA
     * @param password  is the password of Movie Goer in MOBLIMA
     * @return The created {@link MovieGoer} object
     */
    public static MovieGoer createGoerSeniorCitizen(String name, String email, String mobileNum, String username,
            String password) {
        String UUID = String.format("MG%04d", DatabaseHandler.generateUUID(Database.MOVIE_GOER));
        MovieGoer goer = new SeniorCitizen(UUID, name, email, mobileNum, username, password);
        DatabaseManager.saveUpdateToDatabase(UUID, goer, Database.MOVIE_GOER);
        return goer;
    }

    /**
     * Method to create a {@link Student} instance and save to database
     * 
     * @param name      is the name of the Movie Goer
     * @param email     is the email address of the Movie Goer
     * @param mobileNum is the mobile number of the Movie Goer
     * @param username  is the username of Movie Goer in MOBLIMA
     * @param password  is the password of Movie Goer in MOBLIMA
     * @return The created {@link MovieGoer} object
     */
    public static MovieGoer createGoerStudent(String name, String email, String mobileNum, String username,
            String password) {
        String UUID = String.format("MG%04d", DatabaseHandler.generateUUID(Database.MOVIE_GOER));
        MovieGoer goer = new Student(UUID, name, email, mobileNum, username, password);
        DatabaseManager.saveUpdateToDatabase(UUID, goer, Database.MOVIE_GOER);
        return goer;
    }

    /**
     * Method to sort all movies according to overall rating or tickets sales.
     * This method will check the permission of the user to access the sorted Top 5
     * data of movie.
     * If the user has the permission, then it will call the
     * {@link MovieManager#sortMovie(ArrayList, String)} and return the sorted movie
     * list,
     * else it will return null.
     * 
     * @param choice is the parameter to sort the list of movie
     * @param movies is the ArrayList of {@link Movie} to be sorted
     * @param staff  is to check whether the user is a staff.
     * @return The filtered ArrayList of {@link Cinema} instances
     */
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

    /**
     * Method to add booking history to {@link MovieGoer} object.
     * 
     * @param history is the {@link BookingHistory} to be added
     * @param goer    is the {@link MovieGoer} object that the history is added to.
     */
    public static void addBookingHistory(BookingHistory history, MovieGoer goer) {
        goer.addBookingHistory(history);
    }
}
