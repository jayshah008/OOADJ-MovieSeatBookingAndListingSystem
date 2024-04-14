package src.view;

import java.util.*;
import src.controller.*;
import src.model.*;
import src.model.enums.*;
import src.handler.*;

/**
 * View for movie goer of the seating plan of the movie at that cinema
 * 
 * @author Lee Juin, Yeek Sheng
 * @version 1.0
 */
public class SeatingPlanView {
    /**
     * ArrayList of {@link Seat} in the cinema
     */
    private ArrayList<Seat> seatingPlan;

    /**
     * Showing time of the movie scheduled {@link DateTime}
     */
    private DateTime showingTime;

    /**
     * Cinema showing the movie {@link Cinema}
     */
    private Cinema cinema;

    /**
     * Schedule of the movie {@link MovieSchedule}
     */
    private MovieSchedule movieSchedule;

    /**
     * MovieGoer object {@link MovieGoer}
     */
    private MovieGoer movieGoer;

    /**
     * Payment View {@link PaymentView}
     */
    private PaymentView paymentView;

    /**
     * Payment object {@link Payment}
     */
    private Payment paymentCreated;

    /**
     * MovieTicket view {@link MovieTicketView}
     */
    private MovieTicketView movieTicketView;

    /**
     * ArrayList of {@link MovieTicket} objects
     */
    private ArrayList<MovieTicket> movieTicketListCreated;

    /**
     * Seat object chosen {@link Seat}
     */
    private Seat seatBooked;

    /**
     * Booking History of the movieGoer {@link BookingHistory}
     */
    private BookingHistory bookingHistoryCreated;

    /**
     * Error Message of the view
     */
    private String errorMessage;

    /**
     * ArrayList of seatID as String
     */
    private ArrayList<String> seatIDList;

    /**
     * Current movie ticket price of one ticket
     */
    private double currentMovieTicketPrice;

    /**
     * Total movie ticket price of all tickets chosen
     */
    private double totalMovieTicketPrice;

    /**
     * Creates a new SeatingPlanView with chosen movieSchedule, cinema, showingPlan
     * and the movieGoer
     * 
     * @param movieSchedule movieSchedule chosen {@link MovieSchedule}
     * @param showTime      show time of the movie
     * @param cinema        cinema chosen {@link Cinema}
     * @param seatingPlan   ArrayList of {@link Seat} objects
     * @param movieGoer     movieGoer accessing the view {@link MovieGoer}
     */
    public SeatingPlanView(MovieSchedule movieSchedule, DateTime showTime, Cinema cinema, ArrayList<Seat> seatingPlan,
            MovieGoer movieGoer) {
        this.seatingPlan = seatingPlan;
        this.cinema = cinema;
        this.movieSchedule = movieSchedule;
        this.movieGoer = movieGoer;
        this.errorMessage = "";
        this.showingTime = showTime;
        this.seatIDList = new ArrayList<>();
        this.currentMovieTicketPrice = 0;
        this.totalMovieTicketPrice = 0;
    }

    /**
     * Method to print out the floor map of the cinema choice to display the seats
     */
    public void printSeatingPlan() {
        if (this.cinema.getCinemaClass() == CinemaClass.PLATINUM) {
            System.out.println("");
            System.out.println("");
            SeatManager.printPlatinumCinemaFloorMap(this.seatingPlan);
        } else {
            System.out.println("");
            System.out.println("");
            SeatManager.printStandardCinemaFloorMap(this.seatingPlan);
        }
    }

    /**
     * Method to print the boiler plate and displays the choice that the user has
     * Displays the cinemaID, movieTitle, showingTime, seatingPlan, seats in cart
     * currently
     */
    public void printMenu() {
        System.out.println(this.cinema.getUUID());
        Movie movie = MovieManager.getMovieByUUID(this.movieSchedule.getMovieOnShow());
        MainView.printBoilerPlate("Seat Booking");
        System.out.println("Cinema ID: " + this.cinema.getUUID());
        System.out.println("Movie Showing: " + movie.getMovieTitle());
        System.out.println("Showing Time: " + this.showingTime.getTimeNow());
        this.printSeatingPlan();
        this.printSeatInCart();
        MainView.printMenuContent("""

                Select from the following actions:

                01. Add Seat into Booking Cart
                02. Check Out and Proceed To Payment
                03. Quit and return back
                """);
    }

    /**
     * Method to print the seatID and price of the seats chosen by the movieGoer
     */
    public void printSeatInCart() {
        String content = "\nSeat in cart: \n";

        for (int i = 0; i < this.seatIDList.size(); i++) {
            String seatID = this.seatIDList.get(i);
            Seat seat = SeatManager.getSeatBySeatID(seatID, this.seatingPlan, this.cinema);
            String index = String.format("%02d. ", i + 1);
            String payload = String.format(index + seatID + "\t" + seat.getSeatType().getDisplayName() + "\n");
            content = content + payload;
        }

        String payload = String.format("Total price: \t%.2f", this.totalMovieTicketPrice);
        content = content + payload;
        MainView.printMenuContent(content);
    }

    /**
     * Method to take in the choice of the user for the booking of a new seat
     * Creates a PaymentView from the current cinema and schedule with total price
     * of MovieTickets
     * Creates a MovieTicketView from the seatIDs chosen at this cinema and schedule
     */
    public void appContent() {
        int choice = -1;

        do {
            if (MovieMenuView.exit) {
                this.errorMessage = "";
                return;
            }

            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();
            if (choice < 0 || choice > 3) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            switch (choice) {
                case 1:
                    UIHandler.clearScreen();
                    System.out.println(this.errorMessage);
                    this.printMenu();
                    System.out.println("Enter the seat ID to be booked: ");
                    String seatID = InputHandler.stringHandler();
                    int index = SeatManager.seatIDConverter(seatID, this.cinema);
                    if (index == -1) {
                        this.errorMessage = "Error! Please enter a valid seat ID!";
                        continue;
                    }
                    this.seatBooked = this.seatingPlan.get(index);

                    if (SeatManager.bookSeat(seatID, this.movieSchedule, this.seatingPlan, this.cinema, true)) {
                        // add seatID into seatIDList => add new ticket into bucket list
                        seatIDList.add(seatID);
                        String movieUUID = this.movieSchedule.getMovieOnShow();
                        if (this.seatBooked.getSeatType() == SeatType.STANDARD) {
                            this.currentMovieTicketPrice = PaymentManager.calculateMovieTicketPrice(this.movieGoer,
                                    this.cinema.getUUID(), movieUUID, this.seatBooked.getUUID(), this.showingTime);
                        } else {
                            seatIDList.add(SeatManager.getNextSeatID(seatID, cinema));
                            this.currentMovieTicketPrice = PaymentManager.calculateMovieTicketPrice(this.movieGoer,
                                    this.cinema.getUUID(), movieUUID, this.seatBooked.getUUID(), this.showingTime) * 2;
                        }
                        this.totalMovieTicketPrice += this.currentMovieTicketPrice;
                        this.errorMessage = "Booking has been made!";
                    } else {
                        this.errorMessage = "Error! Booking cannot be made on the seat selected!";
                    }
                    break;

                case 2:
                    // create new payment + new movie ticket list => then put into new booking
                    // history

                    // cinema code is the last 3 characters in cinema UUID
                    if (this.seatIDList.size() == 0) {
                        this.errorMessage = "Error! Please book a seat before proceeding to payment!";
                        continue;
                    }
                    String cinemaCode = CinemaManager.getCinemaCode(this.cinema);
                    this.paymentView = new PaymentView(cinemaCode, this.totalMovieTicketPrice, this.movieSchedule);
                    this.errorMessage = "";
                    this.paymentView.appContent();
                    if (MovieMenuView.exit) {
                        this.paymentCreated = this.paymentView.getPayment();
                        Movie movie = MovieManager.getMovieByUUID(this.movieSchedule.getMovieOnShow());
                        this.movieTicketView = new MovieTicketView(this.seatIDList, movie, this.showingTime,
                                this.cinema,
                                this.seatingPlan, this.totalMovieTicketPrice);
                        movieTicketView.printMovieTickets();
                        MovieManager.updateMovieTicketSold(movie, seatIDList.size());
                        this.movieTicketListCreated = this.movieTicketView.getMovieTickets();
                        this.bookingHistoryCreated = BookingHistoryManager.createBookingHistory(
                                this.movieTicketListCreated,
                                this.paymentCreated, this.movieGoer);
                        System.out.println("Press any key to return");
                        InputHandler.stringHandler();
                    }
                    break;

                case 3:
                    this.errorMessage = "";
                    for (int i = 0; i < this.seatIDList.size(); i++) {
                        String _seatID = this.seatIDList.get(i);
                        SeatManager.bookSeat(_seatID, movieSchedule, this.seatingPlan, cinema, false);
                    }
                    this.seatIDList = new ArrayList<>();
                    return;
            }
        } while (true);
    }
}
