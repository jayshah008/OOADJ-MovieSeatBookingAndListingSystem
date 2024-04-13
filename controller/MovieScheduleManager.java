package src.controller;

import src.model.*;
import src.database.*;
import src.handler.*;
import java.util.*;

/**
 * Controller for handling all logic related to {@link MovieSchedule}
 * 
 * @author Lee Juin
 * @version 1.0
 */
public class MovieScheduleManager {

    /**
     * Constructor
     */
    public MovieScheduleManager() {
    }

    /**
     * Method to create a {@link MovieSchedule} and save to database
     * 
     * @param movieUUID         is the unique ID of the movie to be shown
     * @param showingVenuesUUID is the unique ID of all the cinemas showing the
     *                          movie
     * @param seatingPlan       is the copy of the seating plan of all the cinemas
     *                          showing the movie
     * @param showingTime       is all the scheduled time to show the movie for each
     *                          corresponding showing venue
     * @return The created {@link MovieSchedule} instance
     */
    public static MovieSchedule createMovieSchedule(String movieUUID, ArrayList<String> showingVenuesUUID,
            ArrayList<ArrayList<Seat>> seatingPlan, ArrayList<DateTime> showingTime) {

        MovieSchedule movieSchedule = updateMovieSchedule(movieUUID, showingVenuesUUID, showingTime);
        if (movieSchedule != null) {
            return movieSchedule;
        }

        String UUID = String.format("MS%04d", DatabaseHandler.generateUUID(Database.MOVIE_SCHEDULE));
        movieSchedule = new MovieSchedule(UUID, movieUUID, showingVenuesUUID, seatingPlan, showingTime);
        DatabaseManager.saveUpdateToDatabase(UUID, movieSchedule, Database.MOVIE_SCHEDULE);
        return movieSchedule;
    }

    /**
     * Method to update a {@link MovieSchedule}
     * 
     * @param movieUUID         is the movie of which the schedule needs to be
     *                          updated
     * @param showingVenuesUUID is all the showing venues to be added/updated
     * @param showingTimes      is all the showing time corresponding to the venues
     *                          to be added/updated
     * @return The updated {@link MovieSchedule}
     */
    public static MovieSchedule updateMovieSchedule(String movieUUID, ArrayList<String> showingVenuesUUID,
            ArrayList<DateTime> showingTimes) {
        ArrayList<MovieSchedule> movieSchedules = Database.getValueList(Database.MOVIE_SCHEDULE.values());

        if (movieSchedules.size() == 0) {
            return null;
        }

        MovieSchedule movieSchedule = movieSchedules.get(0);
        boolean flag = false;
        for (int i = 0; i < movieSchedules.size(); i++) {
            movieSchedule = movieSchedules.get(i);
            if (movieSchedule.getMovieOnShow().equals(movieUUID)) {
                flag = true;
                break;
            }
        }

        if (!flag) {
            return null;
        }

        for (int i = 0; i < showingVenuesUUID.size(); i++) {
            String showingVenueUUID = showingVenuesUUID.get(i);
            String showingTime = showingTimes.get(i).getTimeNow();
            boolean match = false;
            for (int j = 0; j < movieSchedule.getShowingVenues().size(); j++) {
                String showingVenueUUID_ = movieSchedule.getShowingVenues().get(j);
                String showingTime_ = movieSchedule.getShowingTime().get(j).getTimeNow();

                if (showingVenueUUID.equals(showingVenueUUID_) && showingTime.equals(showingTime_)) {
                    match = true;
                    break;
                }
            }
            if (match) {
                continue;
            }
            movieSchedule.addShowingVenue(showingVenueUUID);
            Cinema cinema = CinemaManager.getCinemaByUUID(showingVenueUUID);
            movieSchedule.addSeatingPlan(cinema.duplicateSeats());
            movieSchedule.addShowingTime(showingTimes.get(i));
        }
        return movieSchedule;
    }

    /**
     * Method to retrieve a movie schedule by the queried movie
     * 
     * @param movie is the target movie
     * @return The {@link MovieSchedule} instance queried
     */
    public static MovieSchedule getMovieScheduleByMovie(Movie movie) {
        ArrayList<MovieSchedule> movieSchedules = Database.getValueList(Database.MOVIE_SCHEDULE.values());

        for (int i = 0; i < movieSchedules.size(); i++) {
            MovieSchedule movieSchedule = movieSchedules.get(i);
            String movieUUID = movieSchedule.getMovieOnShow();

            if (movie.getUUID().equals(movieUUID)) {
                return movieSchedule;
            }
        }
        return null;
    }

    /**
     * Refactored method to reset a movie schedule.
     * 
     * @param movieSchedule is the movie schedule to be resetted
     */
    public static void resetMovieSchedule(MovieSchedule movieSchedule) {
        String movieScheduleUUID = movieSchedule.getUUID();
        ArrayList<String> resetShowingVenues = new ArrayList<>();
        ArrayList<ArrayList<Seat>> resetSeat = new ArrayList<>();
        ArrayList<DateTime> resetShowingTime = new ArrayList<>();
        movieSchedule.setSeatingPlan(resetSeat);
        movieSchedule.setShowingTime(resetShowingTime);
        movieSchedule.setShowingVenues(resetShowingVenues);

        DatabaseManager.saveUpdateToDatabase(movieScheduleUUID, movieSchedule, Database.MOVIE_SCHEDULE);
    }
}
