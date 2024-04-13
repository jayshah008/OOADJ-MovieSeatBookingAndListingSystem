package src.controller;

import src.model.enums.*;
import src.model.*;
import src.database.Database;
import java.util.*;

/**
 * Controller class for handling all the logic related to all users
 * 
 * @author Jonathan Ng
 * @version 1.0
 */
public class UserManager {

    /**
     * Constructor
     */
    public UserManager() {
    }

    /**
     * Method to perform simple login by verifying the user's username and password
     * entered according to the database
     * 
     * @param username is the username of the user
     * @param password is the password of the user
     * @return An {@link CinemaStaff} or {@link MovieGoer} Object based on which
     *         database the details was verified from
     */
    public static Object login(String username, String password) {
        ArrayList<CinemaStaff> cinemaStaffList = Database.getValueList(Database.CINEMA_STAFF.values());
        for (int i = 0; i < cinemaStaffList.size(); i++) {
            CinemaStaff c = cinemaStaffList.get(i);
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                return c;
            }
        }

        ArrayList<MovieGoer> movieGoerList = Database.getValueList(Database.MOVIE_GOER.values());
        for (int i = 0; i < movieGoerList.size(); i++) {
            MovieGoer m = movieGoerList.get(i);
            if (m.getUsername().equals(username) && m.getPassword().equals(password)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Method to check database if the entered username is unique
     * 
     * @param username is the username entered by movie goer
     * @return A boolean flag whether the username is unique or not
     */
    public static boolean checkUniqueUser(String username) {
        ArrayList<CinemaStaff> cinemaStaffList = Database.getValueList(Database.CINEMA_STAFF.values());
        ArrayList<MovieGoer> movieGoerList = Database.getValueList(Database.MOVIE_GOER.values());
        for (int i = 0; i < cinemaStaffList.size(); i++) {
            CinemaStaff cinemaStaff = cinemaStaffList.get(i);

            if (cinemaStaff.getUsername().equals(username)) {
                return false;
            }
        }
        for (int i = 0; i < movieGoerList.size(); i++) {
            MovieGoer movieGoer = movieGoerList.get(i);

            if (movieGoer.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to register for a new movie goer account by creating a
     * {@link MovieGoer} object
     * 
     * @param age       is the {@link MovieGoerAge} of the movie goer
     * @param name      is the name of the movie goer
     * @param username  is the username of the movie goer's account
     * @param password  is the password of the movie goer's account
     * @param email     is the email of the movie goer
     * @param mobileNum is the password of the movie goer
     * @return The created {MovieGoer} object
     */
    public static MovieGoer register(MovieGoerAge age, String name, String username, String password, String email,
            String mobileNum) {

        if (checkUniqueUser(username)) {
            switch (age) {
                case Adult:
                    return MovieGoerManager.createGoerAdult(name, email, mobileNum, username, password);

                case Student:
                    return MovieGoerManager.createGoerStudent(name, email, mobileNum, username, password);

                case SeniorCitizen:
                    return MovieGoerManager.createGoerSeniorCitizen(name, email, mobileNum, username, password);

                case Child:
                    return MovieGoerManager.createGoerStudent(name, email, mobileNum, username, password);

                default:
                    return MovieGoerManager.createGoerAdult(name, email, mobileNum, username, password);
            }
        } else {
            return null;
        }
    }
}
