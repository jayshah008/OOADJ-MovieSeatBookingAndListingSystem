package src.model;

import java.io.Serializable;

public class MovieReview implements Serializable {

    private String UUID;

    private String review;

    private double movieReviewRating;

    private String goerUUID;

    private Movie movie;

    private final static long serialVersionUID = 10L;

    public MovieReview(String UUID, String goerUUID, Movie movie, String review, double movieReviewRating) {
        this.review = review;
        this.movieReviewRating = movieReviewRating;
        this.goerUUID = goerUUID;
        this.movie = movie;
    }

    public String getUUID() {
        return this.UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getMovieReviewRating() {
        return movieReviewRating;
    }

    public void setMovieReviewRating(double movieReviewRating) {
        this.movieReviewRating = movieReviewRating;
    }

    public String getGoerUUID() {
        return goerUUID;
    }

    public void setGoerUUID(String goerUUID) {
        this.goerUUID = goerUUID;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}