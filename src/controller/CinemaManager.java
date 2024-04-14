package src.controller;

import src.model.*;
import src.model.enums.*;
import src.database.*;
import src.handler.*;
import java.util.*;


public class CinemaManager {


    public CinemaManager() {
    }


    public static Cinema createPlatinumCinema(ArrayList<Seat> seats) {
        String UUID = String.format("CN%04d", DatabaseHandler.generateUUID(Database.CINEMA));
        Cinema cinema = new PlatinumCinema(UUID, seats, Database.platinumNumOfRow,
                Database.platinumNumOfSeatsPerRow * Database.platinumNumOfRow);
        DatabaseManager.saveUpdateToDatabase(UUID, cinema, Database.CINEMA);
        return cinema;
    }


    public static Cinema createStandardCinema(ArrayList<Seat> seats) {
        String UUID = String.format("CN%04d", DatabaseHandler.generateUUID(Database.CINEMA));
        Cinema cinema = new StandardCinema(UUID, seats, Database.numOfRows, Database.totalNumOfSeats);
        DatabaseManager.saveUpdateToDatabase(UUID, cinema, Database.CINEMA);
        return cinema;
    }


    public static Cinema createIMaxCinema(ArrayList<Seat> seats) {
        String UUID = String.format("CN%04d", DatabaseHandler.generateUUID(Database.CINEMA));
        Cinema cinema = new IMaxCinema(UUID, seats, Database.numOfRows, Database.totalNumOfSeats);
        DatabaseManager.saveUpdateToDatabase(UUID, cinema, Database.CINEMA);
        return cinema;
    }


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


    public static String getCinemaCode(Cinema cinema) {
        String UUID = cinema.getUUID();
        int length = UUID.length();
        return UUID.substring(length - 3);
    }

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
