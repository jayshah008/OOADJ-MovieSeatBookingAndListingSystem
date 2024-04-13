package src.controller;

import src.model.*;
import src.model.enums.*;
import src.database.*;
import src.handler.*;
import java.util.*;

/**
 * Controller class for handling all logic related to {@link Cinema}
 * 
 * @author Lee Juin
 * @version 1.0
 */
public class CinemaManager {

    /**
     * Constructor
     */
    public CinemaManager() {
    }

    /**
     * Method to create a {@link PlatinumCinema} instance and save to database
     * 
     * @param seats is all the {@link Seat} instances in the cinema
     * @return The created {@link Cinema} object
     */
    public static Cinema createPlatinumCinema(ArrayList<Seat> seats) {
        String UUID = String.format("CN%04d", DatabaseHandler.generateUUID(Database.CINEMA));
        Cinema cinema = new PlatinumCinema(UUID, seats, Database.platinumNumOfRow,
                Database.platinumNumOfSeatsPerRow * Database.platinumNumOfRow);
        DatabaseManager.saveUpdateToDatabase(UUID, cinema, Database.CINEMA);
        return cinema;
    }

    /**
     * Method to create a {@link StandardCinema} instance and save to database
     * 
     * @param seats is all the {@link Seat} instances in the cinema
     * @return The created {@link Cinema} object
     */
    public static Cinema createStandardCinema(ArrayList<Seat> seats) {
        String UUID = String.format("CN%04d", DatabaseHandler.generateUUID(Database.CINEMA));
        Cinema cinema = new StandardCinema(UUID, seats, Database.numOfRows, Database.totalNumOfSeats);
        DatabaseManager.saveUpdateToDatabase(UUID, cinema, Database.CINEMA);
        return cinema;
    }

    /**
     * Method to create a {@link IMaxCinema} instance and save to database
     * 
     * @param seats is all the {@link Seat} instances in the cinema
     * @return The created {@link Cinema} object
     */
    public static Cinema createIMaxCinema(ArrayList<Seat> seats) {
        String UUID = String.format("CN%04d", DatabaseHandler.generateUUID(Database.CINEMA));
        Cinema cinema = new IMaxCinema(UUID, seats, Database.numOfRows, Database.totalNumOfSeats);
        DatabaseManager.saveUpdateToDatabase(UUID, cinema, Database.CINEMA);
        return cinema;
    }

    /**
     * Method to obtain a list of cinemas from a given cineplex, which belongs to
     * the same class
     * 
     * @param cinemaClass is the {@link CinemaClass} to query
     * @param cineplex    is the {@link Cineplex} to query
     * @return The filtered ArrayList of {@link Cinema} instances
     */
    public static ArrayList<Cinema> filterCinemaByClass(CinemaClass cinemaClass, Cineplex cineplex) {
        ArrayList<Cinema> filteredCinemas = new ArrayList<>();
        ArrayList<Cinema> cinemas = cineplex.getCinemas();

        for (int i = 0; i < cinemas.size(); i++) {
            Cinema cinema = cinemas.get(i);
            if (cinema.getCinemaClass() == cinemaClass) {
                filteredCinemas.add(cinema);
            }
        }
        return filteredCinemas;
    }

    /**
     * Method to obtain a list of cinemas from a given cineplex, which are showing
     * the queried movie
     * 
     * @param movie    is the {@link Movie} to query
     * @param cineplex is the {@link Cineplex} to query
     * @return The filtered ArrayList of {@link Cinema} instances
     */
    public static ArrayList<Cinema> filterCinemaByCineplexMovie(Cineplex cineplex, Movie movie) {
        ArrayList<Cinema> filteredCinemas = new ArrayList<>();
        ArrayList<Cinema> cineplexCinemas = cineplex.getCinemas();
        MovieSchedule movieSchedule = MovieScheduleManager.getMovieScheduleByMovie(movie);
        ArrayList<String> showingVenuesUUID = movieSchedule.getShowingVenues();

        for (int i = 0; i < cineplexCinemas.size(); i++) {
            Cinema cineplexCinema = cineplexCinemas.get(i);
            for (int j = 0; j < showingVenuesUUID.size(); j++) {
                String showingVenueUUID = showingVenuesUUID.get(j);
                if (cineplexCinema.getUUID().equals(showingVenueUUID)) {
                    filteredCinemas.add(cineplexCinema);
                }
            }
        }
        return filteredCinemas;
    }

    /**
     * Method to extract cinema code from cinema UUID for printing in transaction ID
     * 
     * @param cinema is the target cinema
     * @return The cinema code
     */
    public static String getCinemaCode(Cinema cinema) {
        String UUID = cinema.getUUID();
        int length = UUID.length();
        return UUID.substring(length - 3);
    }

    /**
     * Method to obtain a cinema by its UUID
     * 
     * @param cinemaUUID is the target cinema's UUID
     * @return The target {@link Cinema}
     */
    public static Cinema getCinemaByUUID(String cinemaUUID) {
        ArrayList<Cinema> cinemas = Database.getValueList(Database.CINEMA.values());

        for (int i = 0; i < cinemas.size(); i++) {
            Cinema cinema = cinemas.get(i);
            if (cinema.getUUID().equals(cinemaUUID)) {
                return cinema;
            }
        }
        return null;
    }
}
