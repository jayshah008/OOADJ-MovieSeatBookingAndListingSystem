package src.controller;

import src.database.Database;
import src.handler.*;
import src.model.*;

/**
 * Controller class for handling all logic related to {@link MovieReview}
 * 
 * @author Jerick, Yeek Sheng
 * @version 1.0
 */
public class MovieReviewManager {

    /**
     * Constructor
     */
    public MovieReviewManager() {
    }

    /**
     * Method to create a {@link MovieReview} instance and save to
     * {@link MovieGoer}, {@link Movie}, and {@link MovieReview} database.
     * 
     * @param movieGoer         is the {@link MovieGoer} who sets the review
     * @param movie             is the movie that the review given to
     * @param review            is the review of the movie
     * @param movieReviewRating is the rating of the movie
     * @return The created {@link MovieReview} object
     */
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
