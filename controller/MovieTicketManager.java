package src.controller;

import src.model.*;
import src.handler.*;
import src.database.*;
import java.util.*;

/**
 * Controller for handling all logic related to {@link MovieTicket}
 * 
 * @author Ci Hui
 * @version 1.0
 */

public class MovieTicketManager {

    /**
     * Constructor
     */
    public MovieTicketManager() {
    }

    /**
     * Method to create a {@link MovieTicket} instance and save to database
     * 
     * @param movieUUID  is the unique ID of the chosen Movie
     * @param showTime   is the Showing Time of the chosen Movie
     * @param cinemaUUID is the unique ID of the chosen Cinema
     * @param seatID     is the front-end displayed seat ID of the chosen Seat
     * @return the created {@link MovieTicket} instance
     */
    public static MovieTicket createMovieTicket(String movieUUID, DateTime showTime, String cinemaUUID, String seatID) {
        String UUID = String.format("MT%04d", DatabaseHandler.generateUUID(Database.MOVIE_TICKET));
        MovieTicket movieTicket = new MovieTicket(UUID, movieUUID, showTime, seatID, cinemaUUID);
        DatabaseManager.saveUpdateToDatabase(UUID, movieTicket, Database.MOVIE_TICKET);
        return movieTicket;
    }

    /**
     * Method to create an Array List of {@link MovieTicket}
     * from the Array List of seat ID booked by the Movie Goer
     * 
     * @param seatID                is the Array List of front-end displayed Seat ID
     *                              booked by the Movie Goer
     * @param movieToWatch          is the {@link Movie} chosen by the Movie Goer
     * @param showTime              is the {@link DateTime} of the Showing Time of
     *                              the Movie chosen by the Movie Goer
     * @param cinema                is the {@link Cinema} chosen by the Movie Goer
     * @param seatingPlan           is the seating plan of the chosen Cinema
     * @param totalMovieTicketPrice is the Total Price of the Movie Tickets booked
     *                              by the Movie Goer
     * @return the created Array List of {@link MovieTicket} instance
     */
    public static ArrayList<MovieTicket> createMovieTicketList(ArrayList<String> seatID, Movie movieToWatch,
            DateTime showTime, Cinema cinema, ArrayList<Seat> seatingPlan, double totalMovieTicketPrice) {

        ArrayList<MovieTicket> movieTickets = new ArrayList<MovieTicket>();

        for (int i = 0; i < seatID.size(); i++) {
            String movieUUID = movieToWatch.getUUID();
            String cinemaUUID = cinema.getUUID();
            MovieTicket newMovieTicket = MovieTicketManager.createMovieTicket(movieUUID, showTime, cinemaUUID,
                    seatID.get(i));
            movieTickets.add(newMovieTicket);
        }
        return movieTickets;
    }

}
