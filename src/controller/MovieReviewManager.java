package src.controller;

import src.database.Database;
import src.handler.*;
import src.model.*;

public class MovieReviewManager {


    public MovieReviewManager() {
    }


    public MovieReview createMovieReview(MovieGoer movieGoer, Movie movie, String review, double movieReviewRating) {
        String UUID = String.format("MR%04d", DatabaseHandler.generateUUID(Database.MOVIE_REVIEW));
        String goerUUID = movieGoer.getUUID();
        MovieReview movieReview = new MovieReview(UUID, goerUUID, movie, review, movieReviewRating);
        DatabaseManager.saveUpdateToDatabase(UUID, movieReview, Database.MOVIE_REVIEW);

        movie.addMovieReview(movieReview);
        MovieManager.calculateOverallReviewRating(movie);
        DatabaseManager.saveUpdateToDatabase(movie.getUUID(), movie, Database.MOVIE);
        movieGoer.addReviewHistory(movieReview);
        DatabaseManager.saveUpdateToDatabase(goerUUID, movieGoer, Database.MOVIE_GOER);

        return movieReview;
    }
}
