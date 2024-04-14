package src.view;

import java.util.*;
import src.controller.*;
import src.model.*;
import src.handler.*;
import src.database.*;

/**
 * View for movie goer to access their movie booking history
 * 
 * @author Yeek Sheng
 * @version 1.0
 */
public class MovieBookingHistoryView extends MainView {
    /**
     * ArrayList of {@link BookingHistory} objects
     */
    private ArrayList<BookingHistory> bookingHistories;

    /**
     * ArrayList of movie titles as string
     */
    private ArrayList<String> movieTitle;

    /**
     * ArrayList of {@link DateTime} objects as showingTime
     */
    private ArrayList<DateTime> showingTime;

    /**
     * ArrayList of {@link Cinema} objects
     */
    private ArrayList<Cinema> cinemaList;

    /**
     * ArrayList of {@link Cineplex} objects
     */
    private ArrayList<Cineplex> cineplexList;

    /**
     * ArrayList of an ArrayList of seatIDs as string
     */
    private ArrayList<ArrayList<String>> seatIDList;

    /**
     * MovieGoer object {@link MovieGoer}
     */
    private MovieGoer movieGoer;

    /**
     * Error message of the view
     */
    private String errorMessage;

    /**
     * Creates a new MovieBookingHistoryView with the movieGoer attributes
     * 
     * @param movieGoer movieGoer accessing the view {@link MovieGoer}
     */
    public MovieBookingHistoryView(MovieGoer movieGoer) {
        ArrayList<Cineplex> cineplexes = Database.getValueList(Database.CINEPLEX.values());
        this.bookingHistories = movieGoer.getBookingHistory();
        this.movieGoer = movieGoer;
        this.movieTitle = new ArrayList<String>();
        this.showingTime = new ArrayList<DateTime>();
        this.cinemaList = new ArrayList<Cinema>();
        this.cineplexList = new ArrayList<Cineplex>();
        this.seatIDList = new ArrayList<ArrayList<String>>();
        if (this.bookingHistories.size() > 0) {
            for (int i = 0; i < this.bookingHistories.size(); i++) {
                BookingHistory history = bookingHistories.get(i);
                MovieTicket ticket = history.getMovieTicket().get(0);
                Movie movie = MovieManager.getMovieByUUID(ticket.getMovieToWatch());
                this.movieTitle.add(movie.getMovieTitle());
                this.showingTime.add(ticket.getShowTime());
                Cinema cinema = CinemaManager.getCinemaByUUID(ticket.getShowingVenue());
                this.cinemaList.add(cinema);
                ArrayList<String> IDList = new ArrayList<String>();
                for (int j = 0; j < history.getMovieTicket().size(); j++) {
                    IDList.add(history.getMovieTicket().get(j).getSeatID());
                }
                this.seatIDList.add(IDList);
                String cinemaUUID = ticket.getShowingVenue();
                for (int j = 0; j < cineplexes.size(); j++) {
                    Cineplex cineplex = cineplexes.get(j);
                    for (int k = 0; k < cineplex.getCinemas().size(); k++) {
                        if (cinemaUUID.equals(cineplex.getCinemas().get(k).getUUID())) {
                            cineplexList.add(cineplex);
                            break;
                        }
                    }

                }
            }
        }
        this.errorMessage = "";
    }

    /**
     * Method to print the booking history of the movieGoer from an ArrayList
     */
    public void printBookingHistories() {
        String content = "";
        int count = 0;
        for (int i = 0; i < this.bookingHistories.size(); i++) {
            BookingHistory bookingHistory = this.bookingHistories.get(i);
            String index = String.format("%02d.\n", (i + 1));
            String payload1 = String.format("Booking ID: %s\n", this.bookingHistories.get(i).getUUID());
            String payload2 = String.format("Transaction ID: %s\n", bookingHistory.getPayment().getTransactionID());
            String payload3 = String.format("Movie Title: %s\n", this.movieTitle.get(i));
            String payload4 = String.format("Cineplex Location:\n%s",
                    this.cineplexList.get(i).getCineplexLocation());
            String payload5 = String.format("Cinema: %s %s\n", this.cinemaList.get(i).getUUID(),
                    this.cinemaList.get(i).getCinemaClass().getDisplayName());
            String payload6 = String.format("Showing Time: %s\n\n", showingTime.get(i).getTimeNow());
            content = content + index + payload1 + payload2 + payload3 + payload4 + payload5 + payload6;
            count = i + 1;
        }
        String index = String.format("%02d. ", (count + 1));
        String payload = String.format(index + "Quit and return back\n");
        content = content + payload;
        MainView.printMenuContent(content);
    }

    /**
     * Method to print the boiler plate and calls printBookingHistories
     */
    public void printMenu() {
        MainView.printBoilerPlate("Booking Histories");
        this.printBookingHistories();
    }

    /**
     * Method to take in choice of user and prints the particular movie ticket using
     * MovieTicketView
     */
    public void appContent() {
        int choice = -1;

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();

            if (choice == -1 || choice < 0 || choice > this.bookingHistories.size() + 1) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            if (choice == (this.bookingHistories.size() + 1)) {
                this.errorMessage = "";
                return;
            } else {
                Movie movie = MovieManager.getMovieByUUID(
                        this.bookingHistories.get(choice - 1).getMovieTicket().get(0).getMovieToWatch());
                MovieTicketView ticketView = new MovieTicketView(this.seatIDList.get(choice - 1),
                        movie,
                        this.showingTime.get(choice - 1), this.cinemaList.get(choice - 1),
                        this.cinemaList.get(choice - 1).getSeats(),
                        this.bookingHistories.get(choice - 1).getPayment().getMovieTicketPrice());
                ticketView.printMovieTickets();
                this.errorMessage = "";
            }
            if (MovieMenuView.exit) {
                this.errorMessage = "";
                return;
            }
            System.out.println("Press any key to return: ");
            String dummy = InputHandler.stringHandler();
        } while (true);
    }

}