package src.controller;

import src.model.*;
import src.handler.*;
import src.database.*;
import java.util.*;



public class MovieTicketManager {

    public MovieTicketManager() {
    }


    public static MovieTicket createMovieTicket(String movieUUID, DateTime showTime, String cinemaUUID, String seatID) {
        String UUID = String.format("MT%04d", DatabaseHandler.generateUUID(Database.MOVIE_TICKET));
        MovieTicket movieTicket = new MovieTicket(UUID, movieUUID, showTime, seatID, cinemaUUID);
        DatabaseManager.saveUpdateToDatabase(UUID, movieTicket, Database.MOVIE_TICKET);
        return movieTicket;
    }

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
