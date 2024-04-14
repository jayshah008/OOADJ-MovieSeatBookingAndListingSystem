package src.view;

import src.controller.*;
import src.handler.*;
import src.model.*;
import java.util.*;

/**
 * View for movie goer to view the schedules of the movie for its type
 * 
 * @author Yeek Sheng, Jerick
 * @version 1.0
 */
public class MovieScheduleView {
    /**
     * MovieGoer object {@link Movie}
     */
    private Movie movie;

    /**
     * ArrayList of {@link Cinema} objects
     */
    private ArrayList<Cinema> cinemaList;

    /**
     * MovieSchedule object {@link MovieSchedule}
     */
    private MovieSchedule movieSchedule;

    /**
     * MovieGoer object {@link MovieGoer}
     */
    private MovieGoer movieGoer;

    /**
     * ArrayList of {@link Datetime} objects as showingTime
     */
    private ArrayList<DateTime> showingTimes;

    /**
     * ArrayList of integer for indexes
     */
    private ArrayList<Integer> indexList;

    /**
     * SeatingPlanView
     */
    private SeatingPlanView seatingPlanView;

    /**
     * Error message of the view
     */
    private String errorMessage;

    /**
     * Creates a new MovieScheduleView with ArrayList of cinemas, movie, moviegoer
     * Stores the index of the showingVenues that match the list of cinemas showing
     * the movie and its type
     * 
     * @param cinemaList ArrayList of cinemas showing the movie title and type
     *                   {@link Cinema}
     * @param movie      Movie title {@link Movie}
     * @param movieGoer  movieGoer accessing the view {@link MovieGoer}
     */
    public MovieScheduleView(ArrayList<Cinema> cinemaList, Movie movie, MovieGoer movieGoer) {
        this.movie = movie;
        this.cinemaList = cinemaList;
        this.movieSchedule = MovieScheduleManager.getMovieScheduleByMovie(movie);
        this.movieGoer = movieGoer;
        indexList = new ArrayList<Integer>();
        showingTimes = new ArrayList<DateTime>();
        for (int j = 0; j < movieSchedule.getShowingVenues().size(); j++) {
            for (int i = 0; i < cinemaList.size(); i++) {
                if (movieSchedule.getShowingVenues().get(j).equals(cinemaList.get(i).getUUID())) {
                    indexList.add(j);
                    showingTimes.add(movieSchedule.getShowingTime().get(j));
                }
            }
        }
        this.errorMessage = "";
    }

    /**
     * Method to print out the showingTimes of the movie if it is bookable
     * 
     * @return if the movie is available for booking or not
     */
    public boolean printShowingTimes() {
        String content = "\n";
        if (MovieManager.movieBookable(this.movie)) {
            int count = 0;
            for (int i = 0; i < this.showingTimes.size(); i++) {
                String index = String.format("%02d. ", i + 1);
                DateTime showingTime = this.showingTimes.get(i);
                String payload = String.format(index + "%s\n", showingTime.getTimeNow());
                content = content + payload;
                count = i + 1;
            }

            String index = String.format("%02d. ", count + 1);
            String payload = String.format(index + "Quit and return back");
            content = content + payload;
            MainView.printMenuContent(content);
            return true;
        } else {
            content += "This movie is currently not available for booking!";
            MainView.printMenuContent(content);
            return false;
        }
    }

    /**
     * Method to print the boiler plate and calls printShowingTimes
     * 
     * @return if the movie is available for booking or not
     */
    public boolean printMenu() {
        MainView.printBoilerPlate("Showing Schedule for " + this.movie.getMovieTitle());
        return this.printShowingTimes();
    }

    /**
     * Method to call printMenu and takes in the choice of the user to
     * display the seatingPlanView using the showingTime chosen by the user at that
     * cinema
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
            if (this.printMenu()) {
                choice = InputHandler.intHandler();
                if (choice < 1 || choice > showingTimes.size() + 1) {
                    this.errorMessage = "Error! Please enter a valid input";
                    continue;
                }

                if (choice == showingTimes.size() + 1) {
                    this.errorMessage = "";
                    return;
                } else {
                    int pointer = indexList.get(choice - 1);
                    DateTime showTime = this.movieSchedule.getShowingTime().get(pointer);
                    Cinema cinema = CinemaManager.getCinemaByUUID(this.movieSchedule.getShowingVenues().get(pointer));
                    this.seatingPlanView = new SeatingPlanView(this.movieSchedule, showTime,
                            cinema, this.movieSchedule.getSeatingPlan().get(pointer), this.movieGoer);
                    this.errorMessage = "";
                    this.seatingPlanView.appContent();
                }
            } else {
                System.out.println("Press any key to return");
                InputHandler.stringHandler();
                this.errorMessage = "";
                return;
            }
        } while (true);

    }
}
