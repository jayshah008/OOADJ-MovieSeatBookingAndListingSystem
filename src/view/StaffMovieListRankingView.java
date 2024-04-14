package src.view;

import java.util.*;
import src.controller.*;
import src.database.*;
import src.handler.*;
import src.model.*;


public class StaffMovieListRankingView extends MainView {

    private String errorMessage;


    public StaffMovieListRankingView() {
        this.errorMessage = "";
    }

 
    public void printMenu() {
        MainView.printBoilerPlate("Staff Rank Top 5");
        MainView.printMenuContent("""

                How would you like to rank the movies in terms of?

                01. Rank by ticket sales
                02. Rank by overall reviewers' ratings
                03. Quit and return back
                """);
    }


    public void printTop5(int choice) {
        ArrayList<Movie> movies = Database.getValueList(Database.MOVIE.values());
        String content = "\n";
        String payload;
        String index;

        switch (choice) {
            case 1:
                MainView.printBoilerPlate("Ranking by ticket sales");
                movies = MovieGoerManager.rankTop5("ticket", movies, true);
                break;
            case 2:
                MainView.printBoilerPlate("Ranking by overall rating");
                movies = MovieGoerManager.rankTop5("ratings", movies, true);
                break;
        }

        int length = (movies.size() > 5) ? 5 : movies.size();

        if (choice == 1) {
            for (int j = 0; j < length; j++) {
                index = String.format("%d. ", j + 1);
                payload = String.format(index + movies.get(j).getMovieTitle() + " [ " +
                        movies.get(j).getMovieType().getDisplayName() + " ] - Tickets sold: "
                        + movies.get(j).getMovieTicketsSold() + "\n");
                content = content + payload;
            }

        } else {
            for (int j = 0; j < length; j++) {
                String rating = String.format("%.1f", movies.get(j).getMovieOverallReviewRating());
                index = String.format("%d. ", j + 1);
                payload = String.format(index + movies.get(j).getMovieTitle() + " [ " +
                        movies.get(j).getMovieType().getDisplayName() + " ] - Overall rating: "
                        + rating + "\n");
                content = content + payload;
            }
        }
        MainView.printMenuContent(content);
        System.out.println("Press any key to return");
        InputHandler.stringHandler();
    }


    public void appContent() {
        int choice = -1;
        do {
            UIHandler.clearScreen();
            System.out.println(this.errorMessage);
            this.printMenu();
            choice = InputHandler.intHandler();
            if (choice < 1 || choice > 3) {
                this.errorMessage = "Error! Please enter a valid input!";
                continue;
            }
            if (choice == 3) {
                this.errorMessage = "";
                return;
            }

            UIHandler.clearScreen();
            this.printTop5(choice);

        } while (true);
    }
}
