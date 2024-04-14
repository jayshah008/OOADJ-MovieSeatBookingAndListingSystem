package src.view;

import java.util.*;
import src.controller.*;
import src.database.*;
import src.handler.*;
import src.model.*;
import src.model.enums.*;

/**
 * View for movie goer to choose the ranking format of top 5 movies
 * 
 * @author Yeek Sheng, Jerick
 * @version 1.0
 */
public class MovieListRankingView extends MainView {
    /**
     * Error message of the view
     */
    private String errorMessage;

    /**
     * Creates a new MovieListRankingView with an empty errorMessage
     */
    public MovieListRankingView() {
        this.errorMessage = "";
    }

    /**
     * Prints the boiler plate and displays the choices of the user
     */
    public void printMenu() {
        MainView.printBoilerPlate("Rank Top 5");
        MainView.printMenuContent("""

                How would you like to rank the movies in terms of?

                01. Rank by ticket sales
                02. Rank by overall reviewers' ratings
                03. Return
                """);
    }

    /**
     * Method to call rankTop5 based on choice and prints ranking
     * Showing movies will be displayed with higher priority over End of show movies
     * 
     * @param choice choice of the user to rank movies by ticket sales or overall
     *               rating
     *               The choice should be 1 or 2
     */
    public void printTop5(int choice) {
        ArrayList<Movie> movies = Database.getValueList(Database.MOVIE.values());
        String content = "\n";
        String payload;
        String index;

        switch (choice) {
            case 1:
                MainView.printBoilerPlate("Ranking by ticket sales");
                movies = MovieGoerManager.rankTop5("ticket", movies, false);
                if (movies == null) {
                    content += "Ranking by Top 5 Movie Sales is unavailable";
                    MainView.printMenuContent(content);
                    System.out.println("Press any key to return");
                    InputHandler.stringHandler();
                    return;
                }
                break;
            case 2:
                MainView.printBoilerPlate("Ranking by overall rating");
                movies = MovieGoerManager.rankTop5("ratings", movies, false);
                if (movies == null) {
                    content += "Ranking by Top 5 Movie Overall Review Ratings is unavailable";
                    MainView.printMenuContent(content);
                    System.out.println("Press any key to return");
                    InputHandler.stringHandler();
                    return;
                }
                break;
        }

        int length = (movies.size() > 5) ? 5 : movies.size();

        int k = 0;
        if (choice == 1) {
            for (int j = 0; j < length; j++) {
                do {
                    if (k == movies.size()) {
                        break;
                    }
                    Movie movie = movies.get(k);
                    if (movie.getMovieShowingStatus() != MovieShowingStatus.END_OF_SHOWING) {
                        break;
                    }
                    k++;
                } while (true);
                if (k == movies.size()) {
                    break;
                }
                index = String.format("%d. ", j + 1);
                payload = String.format(index + movies.get(k).getMovieTitle() + " [ " +
                        movies.get(k).getMovieType().getDisplayName() + " ] - Tickets sold: "
                        + movies.get(k).getMovieTicketsSold() + "\n");
                content = content + payload;
                k++;
            }

        } else {
            for (int j = 0; j < length; j++) {
                do {
                    if (k == movies.size()) {
                        break;
                    }
                    Movie movie = movies.get(k);
                    if (movie.getMovieShowingStatus() != MovieShowingStatus.END_OF_SHOWING) {
                        break;
                    }
                    k++;
                } while (true);
                if (k == movies.size()) {
                    break;
                }
                String rating = String.format("%.1f", movies.get(k).getMovieOverallReviewRating());
                index = String.format("%d. ", j + 1);
                payload = String.format(index + movies.get(k).getMovieTitle() + " [ " +
                        movies.get(k).getMovieType().getDisplayName());
                if (movies.get(k).getMovieReviews().size() <= 1) {
                    payload += " ] - Overall rating: Not Available\n";
                } else {
                    payload += " ] - Overall rating: " + rating + "\n";
                }
                content = content + payload;
                k++;
            }
        }
        MainView.printMenuContent(content);
        System.out.println("Press any key to return");
        InputHandler.stringHandler();
    }

    /**
     * Method to take in choice of user as input for printing of ranking
     */
    public void appContent() {
        int choice = -1;
        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();
            if (choice == 3) {
                this.errorMessage = "";
                return;
            }
            if (choice < 1 || choice > 3) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            UIHandler.clearScreen();
            this.printTop5(choice);

        } while (true);
    }
}
