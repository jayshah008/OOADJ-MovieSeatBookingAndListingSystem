package src.view;

import src.model.*;
import src.model.enums.*;
import src.controller.*;
import src.handler.*;
import java.util.*;

/**
 * View for movie goer to view the movie tickets after successful payment
 * 
 * @author Lee Juin, Yeek Sheng, Jerick
 * @version 1.0
 */
public class MovieTicketView {
    /**
     * ArrayList of {@link MovieTicket} objects
     */
    private ArrayList<MovieTicket> movieTicketList;

    /**
     * ArrayList of seatIDs as string
     */
    private ArrayList<String> seatID;

    /**
     * Total movie ticket price for all seats chosen for the movie
     */
    private double totalMovieTicketPrice;

    /**
     * Movie object of the movieTicket {@link Movie}
     */
    private Movie movie;

    /**
     * Creates a new MovieTicket View and a new movieTicketList as an attribute
     * 
     * @param seatID                ArrayList of seatID as string
     * @param movie                 movie object {@link Movie}
     * @param showingTime           showing time chosen {@link DateTime}
     * @param cinema                cinema chosen {@link Cinema}
     * @param seatingPlan           ArrayList of {@link Seat}
     * @param totalMovieTicketPrice Total movie ticekt price of all the seats chosen
     */
    public MovieTicketView(ArrayList<String> seatID, Movie movie, DateTime showingTime, Cinema cinema,
            ArrayList<Seat> seatingPlan, double totalMovieTicketPrice) {
        this.seatID = seatID;
        this.movie = movie;
        this.totalMovieTicketPrice = totalMovieTicketPrice;
        this.movieTicketList = MovieTicketManager.createMovieTicketList(seatID, movie, showingTime, cinema, seatingPlan,
                totalMovieTicketPrice);
    }

    /**
     * Gets the ArrayList of movieTickets
     * 
     * @return ArrayList of {@link MovieTicket}
     */
    public ArrayList<MovieTicket> getMovieTickets() {
        return this.movieTicketList;
    }

    /**
     * Method that prints out the movie tickets purchased with information such as:
     * movieName, movieType, cineplex, cinemaClass, cinemaID, showingTime, price of
     * a movieTicket
     */
    public void printMovieTickets() {

        Cinema _cinema = CinemaManager.getCinemaByUUID(this.movieTicketList.get(0).getShowingVenue());
        System.out.println(this.movieTicketList.get(0).getShowingVenue());
        Cineplex targetCineplex = CineplexManager.getCineplexByCinema(_cinema);

        // we assume that all ticket has the same Cinema, Movie and DateTime, but
        // different Seat (seatID)
        Movie movie = MovieManager.getMovieByUUID(this.movieTicketList.get(0).getMovieToWatch());
        Cinema cinema = CinemaManager.getCinemaByUUID(this.movieTicketList.get(0).getShowingVenue());
        DateTime dateTime = this.movieTicketList.get(0).getShowTime();

        String movieName = movie.getMovieTitle();
        MovieType movieType = movie.getMovieType();
        String cineplex = targetCineplex.getCineplexName();
        CinemaClass cinemaClass = cinema.getCinemaClass();
        String cinemaId = cinema.getUUID();
        int date = dateTime.getDate();
        int month = dateTime.getMonth();
        int year = dateTime.getYear();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        double pricePerMovieTicket = totalMovieTicketPrice / this.movieTicketList.size();

        UIHandler.clearScreen();
        MainView.printBoilerPlate("Movie Tickets");
        System.out.println("Total number of movie ticket: " + this.movieTicketList.size());
        System.out.println("[ Please keep it(them) as the proof for entrance ]");
        System.out.println("");
        for (int i = 0; i < this.movieTicketList.size(); i++) {

            System.out.println("══════════════════════════════════════");
            System.out.println("                MOBLIMA               ");
            System.out.println("                                      ");
            System.out.println("             MOVIE TICKET " + (i + 1));
            System.out.println("___________________________________");
            System.out.println("Movie Name: " + movieName);
            System.out.println("Movie Type: " + movieType.getDisplayName());
            System.out.println("Cineplex: " + cineplex);
            System.out.println("Cinema Class: " + cinemaClass.getDisplayName());
            System.out.println("Cinema ID: " + cinemaId); // Cinema uuid -> Cinema Hall Number (ex. Hall 3)
            System.out.println("Showing Date: " + date + "/" + month + "/" + year);
            System.out.println("Showing Time: " + String.format("%02d", hour) + ":" + String.format("%02d", minute));
            System.out.println("Seat ID: " + this.seatID.get(i)); // seat uuid -> seat ID
            System.out.println("Price ($)/ ticket: " + String.format("%.2f", pricePerMovieTicket));
            System.out.println("____________________________________");
            System.out.println("                                    ");
            System.out.println("          Enjoy Your Movie!         ");
            System.out.println("═════════════════════════════════════");
            System.out.println("");
        }
    }
}
