package src.view;

import src.model.*;
import src.model.enums.*;
import src.controller.*;
import src.handler.*;
import java.util.*;


public class MovieTicketView {

    private ArrayList<MovieTicket> movieTicketList;

 
    private ArrayList<String> seatID;


    private double totalMovieTicketPrice;

   
    private Movie movie;


    public MovieTicketView(ArrayList<String> seatID, Movie movie, DateTime showingTime, Cinema cinema,
            ArrayList<Seat> seatingPlan, double totalMovieTicketPrice) {
        this.seatID = seatID;
        this.movie = movie;
        this.totalMovieTicketPrice = totalMovieTicketPrice;
        this.movieTicketList = MovieTicketManager.createMovieTicketList(seatID, movie, showingTime, cinema, seatingPlan,
                totalMovieTicketPrice);
    }


    public ArrayList<MovieTicket> getMovieTickets() {
        return this.movieTicketList;
    }

 
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
            System.out.println("                MOOADJ                ");
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
