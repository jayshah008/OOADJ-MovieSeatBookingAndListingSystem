package src.view;

import src.handler.*;
import src.model.enums.*;
import src.model.*;
import src.controller.*;
import src.database.*;
import java.util.*;

/**
 * View for cinema staff to add a new movie into the database
 * 
 * @author Jonathan Ng
 * @version 1.0
 */
public class StaffAddMovieView {
    /**
     * Error message of this view
     */
    private String errorMessage;

    /**
     * Constructor
     */
    public StaffAddMovieView() {
        this.errorMessage = "";
    }

    /**
     * Prints the boiler plate
     */
    public void printMenu() {
        MainView.printBoilerPlate("Add New Movies");
    }

    /**
     * Prints the boiler plate for query of Movie Age Rating
     */
    public void printAgeRating() {
        MainView.printMenuContent("""
                Enter the Age Rating

                1. G (General)
                2. PG (Parental Guidance)
                3. PG13 (Parental Guidance for under 13 years old)
                4. NC16 (No children under 16 years old)
                5. M18 (Mature content for above 18 years old)
                6. R21 (Restricted for above 21 years old)
                    """);
    }

    /**
     * Prints the boiler plate for query of Movie Showing Status
     */
    public void printShowingStatus() {
        MainView.printMenuContent("""
                Enter the showing status

                1. Coming Soon
                2. Preview
                3. Now Showing
                """);
    }

    /**
     * Prints the boiler plate for query of Movie Type
     */
    public void printMovieType() {
        MainView.printMenuContent("""
                Enter the movie type

                1. Standard Movie
                2. Blockbuster Movie
                3. 3D Movie
                """);
    }

    /**
     * Prints the boiler plate for query of Cinema Class
     */
    public void printCinemaClass() {
        MainView.printMenuContent("""
                Enter the cinema class for this movie

                1. Standard Cinema
                2. Platinum Cinema
                3. IMAX Cinema
                    """);
    }

    /**
     * Prints the Cineplexes showing the new movie
     */
    public void printCineplex() {
        ArrayList<Cineplex> cineplexes = Database.getValueList(Database.CINEPLEX.values());
        String content = "\nEnter the cineplex showing this movie\n\n";

        for (int i = 0; i < cineplexes.size(); i++) {
            String index = String.format("%02d.\n", i + 1);
            String payload = String.format(index + cineplexes.get(i).getCineplexName() + "\n");
            payload += (cineplexes.get(i).getCineplexLocation() + "\n");
            content += payload;
        }

        MainView.printMenuContent(content);
    }

    /**
     * Prints the showing time for the new movie
     * 
     * @param showingVenue is the {@link Cinema} that are showing the movie
     */
    public void printShowingTime(Cinema showingVenue) {
        String content = "\n" + "Showing Time for " + showingVenue.getUUID() + "\n";
        MainView.printMenuContent(content);
    }

    /**
     * Method to call printMenu and addNewMovie
     */
    public void appContent() {
        UIHandler.clearScreen();
        this.printMenu();
        this.addNewMovie();
    }

    /**
     * Method to add new movies into the database
     * Upon all successful choices of movie details, movieAdder under
     * CinemaStaffManager is called and a new movie is added into the database
     */
    public void addNewMovie() {
        MovieAgeRating movieAgeRating;
        MovieShowingStatus movieShowingStatus;
        double duration;
        int movieTypeChoice;

        System.out.println("Enter the name of new movie");
        String title = InputHandler.stringHandler();

        int choice = -1;
        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            this.printAgeRating();
            choice = InputHandler.intHandler();
            if (choice < 1 || choice > 7) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            movieAgeRating = MovieAgeRating.values()[choice - 1];
            this.errorMessage = "";
            break;
        } while (true);

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            this.printShowingStatus();
            choice = InputHandler.intHandler();
            if (choice < 1 || choice > 3) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            movieShowingStatus = MovieShowingStatus.values()[choice - 1];
            this.errorMessage = "";
            break;
        } while (true);

        ArrayList<String> movieCast = new ArrayList<>();
        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            System.out.println("Enter the number of movie cast/casts for " + title + ": ");
            int numberOfCasts = InputHandler.intHandler();
            if (numberOfCasts < 2) {
                this.errorMessage = "Error! The movie must have minimum 2 casts!";
                continue;
            }
            for (int i = 0; i < numberOfCasts; i++) {
                UIHandler.clearScreen();
                System.out.println(this.errorMessage);
                this.printMenu();
                System.out.println("Enter the name of cast " + (i + 1) + ": ");
                String castName = InputHandler.stringHandler();
                movieCast.add(castName);
            }
            this.errorMessage = "";
            break;
        } while (true);

        UIHandler.clearScreen();
        System.out.println(this.errorMessage);
        this.printMenu();
        System.out.println("Enter the name of the director for " + title + ":");
        String director = InputHandler.stringHandler();

        UIHandler.clearScreen();
        System.out.println(this.errorMessage);
        this.printMenu();
        System.out.println("Enter the synopsis for " + title + ": ");
        String synopsis = InputHandler.stringHandler();

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            System.out.println("Enter duration for " + title);
            duration = InputHandler.doubleHandler();
            if (duration < 0) {
                this.errorMessage = "Duration cannot be negative";
                continue;
            }
            this.errorMessage = "";
            break;
        } while (true);

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            this.printMovieType();
            movieTypeChoice = InputHandler.intHandler();
            if (movieTypeChoice < 0 || movieTypeChoice > 3) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            this.errorMessage = "";
            break;
        } while (true);

        ArrayList<Cinema> showingVenue;
        ArrayList<String> showingVenueUUID = new ArrayList<>();
        ArrayList<ArrayList<Seat>> seatingPlan = new ArrayList<ArrayList<Seat>>();
        ArrayList<DateTime> showingTime = new ArrayList<DateTime>();
        CinemaClass cinemaClass;
        Cineplex cineplex;
        ArrayList<Cineplex> cineplexes = Database.getValueList(Database.CINEPLEX.values());
        Collections.sort(cineplexes);

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            this.printCinemaClass();
            choice = InputHandler.intHandler();
            if (choice < 1 || choice > 3) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            cinemaClass = CinemaClass.values()[choice - 1];
            this.errorMessage = "";
            break;
        } while (true);

        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            this.printCineplex();
            choice = InputHandler.intHandler();
            if (choice < 1 || choice > cineplexes.size()) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            cineplex = cineplexes.get(choice - 1);
            this.errorMessage = "";
            break;
        } while (true);

        showingVenue = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass, cineplex));
        for (int i = 0; i < showingVenue.size(); i++) {
            showingVenueUUID.add(showingVenue.get(i).getUUID());
            seatingPlan.add(showingVenue.get(i).duplicateSeats());
        }

        int numOfShowingTimes = showingVenue.size();
        for (int i = 0; i < numOfShowingTimes; i++) {
            UIHandler.clearScreen();
            this.printMenu();
            this.printShowingTime(showingVenue.get(i));
            DateTime time = CinemaStaffManager.queryDate();
            showingTime.add(time);
        }

        CinemaStaffManager.movieAdder(title, movieAgeRating, movieShowingStatus, movieCast, director,
                synopsis, duration, movieTypeChoice, showingVenueUUID, seatingPlan, showingTime);
    }

}
