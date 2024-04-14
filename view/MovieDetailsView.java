package src.view;

import java.util.*;
import src.controller.*;
import src.model.*;
import src.handler.*;

/**
 * View for movie goer to view the details of the movie
 * 
 * @author Yeek Sheng, Jerick
 * @version 1.0
 */
public class MovieDetailsView extends MainView {
    /**
     * Title of the movie
     */
    private String movieTitle;

    /**
     * Synopsis of the movie
     */
    private String synopsis;

    /**
     * ArrayList of {@link MovieReview} objects
     */
    private ArrayList<MovieReview> pastReviews;

    /**
     * MovieGoer objects {@link MovieGoer}
     */
    private MovieGoer movieGoer;

    /**
     * Error message of the view
     */
    private String errorMessage;

    /**
     * ArrayList of the different types of {@link Movie} objects
     */
    private ArrayList<Movie> listOfMovieTypes;

    /**
     * totalOverallReviewRating of the Movie
     */
    private double totalOverallReviewRating;

    /**
     * Creates a new MovieDetailsView with the title and movieGoer
     * 
     * @param title     title of the movie
     * @param movieGoer movieGoer accessing the view
     */
    public MovieDetailsView(String title, MovieGoer movieGoer) {
        this.movieTitle = title;
        this.movieGoer = movieGoer;
        this.errorMessage = "";
        this.listOfMovieTypes = MovieManager.getMovieList(movieTitle);
        this.synopsis = this.listOfMovieTypes.get(0).getMovieSynopsis();
        this.pastReviews = new ArrayList<>();
        this.totalOverallReviewRating = 0;
        for (int i = 0; i < this.listOfMovieTypes.size(); i++) {
            Movie movie = this.listOfMovieTypes.get(i);
            this.pastReviews.addAll(movie.getMovieReviews());
            this.totalOverallReviewRating += movie.getMovieOverallReviewRating();
        }
        this.totalOverallReviewRating /= this.listOfMovieTypes.size();
    }

    /**
     * Method to print out the basic details of the movie: Director, Cast, Movie
     * Type
     * Overall rating will be printed if available
     */
    public void printMovieDetails() {
        System.out.println("Movie Director: " + this.listOfMovieTypes.get(0).getMovieDirector());
        if (this.pastReviews.size() < 2) {
            System.out.println("Overall Rating: Not Available!");
        } else {
            String rating = String.format("%.2f", this.totalOverallReviewRating);
            System.out.println("Overall Rating: " + rating);
        }
        System.out.println("Movie Age Rating: " + this.listOfMovieTypes.get(0).getMovieAgeRating().getDisplayName());
        System.out.print("Movie Cast: ");
        for (int j = 0; j < this.listOfMovieTypes.get(0).getMovieCast().size(); j++) {
            System.out.print(this.listOfMovieTypes.get(0).getMovieCast().get(j));
            System.out.print(", ");
        }
        System.out.println("...");
        System.out.print("Movie Type: ");
        for (int i = 0; i < this.listOfMovieTypes.size(); i++) {
            System.out.print(this.listOfMovieTypes.get(i).getMovieType().getDisplayName() + " ("
                    + this.listOfMovieTypes.get(i).getMovieShowingStatus().getDisplayName() + ")");
            if (i != this.listOfMovieTypes.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("");

    }

    /**
     * Method to print the boiler plate, movie details and display the choices of
     * the user
     */
    public void printMenu() {
        MainView.printBoilerPlate(this.movieTitle);
        this.printMovieDetails();
        MainView.printMenuContent("""

                Select an option to view further information about the movie:

                01. View Synopsis
                02. View Past Reviews
                03. Booking Query
                04. Review the Movie
                05. Quit and return back
                """);
    }

    /**
     * Method to print out further details of the movie: Synopsis, Past Reviews
     * User will be able to add reviews and view the different types of the movie
     * for booking
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
            if (choice < 0 || choice > 5) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            switch (choice) {
                case 1:
                    UIHandler.clearScreen();
                    this.errorMessage = "";
                    this.printSynopsis();
                    break;
                case 2:
                    UIHandler.clearScreen();
                    this.errorMessage = "";
                    this.printPastReviews();
                    break;
                case 3:
                    MovieTypeView typeView = new MovieTypeView(this.listOfMovieTypes.get(0).getMovieTitle(),
                            this.movieGoer);
                    this.errorMessage = "";
                    typeView.appContent();
                    break;
                case 4:
                    UIHandler.clearScreen();
                    this.errorMessage = "";
                    this.printAddReview();
                    break;
                case 5:
                    this.errorMessage = "";
                    return;
            }
        } while (true);
    }

    /**
     * Method to print the synopsis of the movie
     */
    public void printSynopsis() {
        MainView.printBoilerPlate("Synopsis of " + this.movieTitle);
        MainView.printMenuContent(this.synopsis);
        System.out.println("Press any key to return: ");
        InputHandler.stringHandler();
    }

    /**
     * Method that will print each of the reviews and ratings
     * Reviews will not be displayed if number of reviews is less than 2
     */
    public void printPastReviews() {
        MainView.printBoilerPlate("Past Reviews of " + this.movieTitle);

        if (this.pastReviews.size() <= 0) {
            MainView.printMenuContent("Reviews are not available yet!");
        } else {
            String content = "\n";

            for (int i = 0; i < pastReviews.size(); i++) {
                String index = String.format("%d. ", i + 1);
                String payload1 = String.format(index + "Rating: " + this.pastReviews.get(i).getMovieReviewRating());
                String payload2 = String.format("\n   Review: %s\n", this.pastReviews.get(i).getReview());
                content = content + payload1 + payload2;
            }
            MainView.printMenuContent(content);
        }

        System.out.println("Press any key to return: ");
        InputHandler.stringHandler();
    }

    /**
     * Method to take in the review and rating of the user for createMovieReview
     */
    public void printAddReview() {
        String errorMessage = "";
        Movie movie;

        do {
            UIHandler.clearScreen();
            System.out.println(errorMessage);
            MainView.printBoilerPlate("Adding Reviews for " + this.movieTitle);
            this.printMovieType();
            int choice = InputHandler.intHandler();

            if (choice < 1 || choice > this.listOfMovieTypes.size() + 1) {
                errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            if (choice == this.listOfMovieTypes.size() + 1) {
                return;
            }
            movie = this.listOfMovieTypes.get(choice - 1);
            break;
        } while (true);

        UIHandler.clearScreen();
        System.out.println(errorMessage);
        MainView.printBoilerPlate("Adding Reviews for " + this.movieTitle);
        System.out.println("Give a review for the movie: ");
        String review = InputHandler.stringHandler();

        UIHandler.clearScreen();
        System.out.println(errorMessage);
        MainView.printBoilerPlate("Adding Reviews for " + this.movieTitle);
        System.out.println("Give a rating for the movie: (0-5)");
        double rating = InputHandler.doubleHandler();
        while (rating < 0 || rating > 5) {
            errorMessage = "Error! Please enter a valid input!";
            UIHandler.clearScreen();
            System.out.println(errorMessage);
            MainView.printBoilerPlate("Adding Reviews for " + this.movieTitle);
            System.out.println("Give a rating for the movie: (0-5)");
            rating = InputHandler.doubleHandler();
        }
        System.out.println("Review Created!!");
        MovieReviewManager manager = new MovieReviewManager();
        manager.createMovieReview(this.movieGoer, movie, review, rating);
    }

    /**
     * Method to print out the different movie types available for the movie
     */
    public void printMovieType() {
        String content = "\nSelect the movie type: \n\n";

        int count = 0;
        for (int i = 0; i < listOfMovieTypes.size(); i++) {
            Movie movie = listOfMovieTypes.get(i);
            String index = String.format("%02d. ", i + 1);
            String payload = String.format(index + "%s\n", movie.getMovieType().getDisplayName());
            content = content + payload;
            count = i + 1;
        }
        String index = String.format("%02d. ", count + 1);
        String payload = String.format(index + "Quit and return back");
        content = content + payload;

        MainView.printMenuContent(content);
    }
}