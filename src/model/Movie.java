package src.model;

import java.io.Serializable;
import java.util.ArrayList;

import src.model.enums.*;

public abstract class Movie implements Serializable {

	private String UUID;

	private String movieTitle;

	private MovieShowingStatus movieShowingStatus;

	private String movieSynopsis;

	private String movieDirector;

	private ArrayList<String> movieCast;

	private double movieOverallReviewRating;

	private ArrayList<MovieReview> movieReviews;

	private MovieAgeRating movieAgeRating;

	private double movieDuration;

	private int movieTicketsSold;

	private final static long serialVersionUID = 8L;

	public Movie(String UUID, String movieTitle, MovieAgeRating movieAgeRating,
			MovieShowingStatus showingStatus,
			ArrayList<String> movieCast, String movieDirector, String movieSynopsis, double movieDuration) {

		this.UUID = UUID;
		this.movieTitle = movieTitle;
		this.movieAgeRating = movieAgeRating;
		this.movieShowingStatus = showingStatus;
		this.movieCast = movieCast;
		this.movieDirector = movieDirector;
		this.movieSynopsis = movieSynopsis;
		this.movieDuration = movieDuration;
		this.movieReviews = new ArrayList<>();
		this.movieTicketsSold = 0;
	}

	public String getUUID() {
		return this.UUID;
	}

	public void setUUID(String UUID) {
		this.UUID = UUID;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public MovieShowingStatus getMovieShowingStatus() {
		return movieShowingStatus;
	}

	public void setMovieShowingStatus(MovieShowingStatus movieShowingStatus) {
		this.movieShowingStatus = movieShowingStatus;
	}

	public String getMovieSynopsis() {
		return movieSynopsis;
	}

	public void setMovieSynopsis(String movieSynopsis) {
		this.movieSynopsis = movieSynopsis;
	}

	public String getMovieDirector() {
		return movieDirector;
	}

	public void setMovieDirector(String movieDirector) {
		this.movieDirector = movieDirector;
	}

	public ArrayList<String> getMovieCast() {
		return movieCast;
	}

	public void setMovieCast(ArrayList<String> movieCast) {
		this.movieCast = movieCast;
	}

	public double getMovieOverallReviewRating() {
		return movieOverallReviewRating;
	}

	public void setMovieOverallReviewRating(double movieOverallReviewRating) {
		this.movieOverallReviewRating = movieOverallReviewRating;
	}

	public ArrayList<MovieReview> getMovieReviews() {
		return movieReviews;
	}

	public void setMovieReviews(ArrayList<MovieReview> movieReviews) {
		this.movieReviews = movieReviews;
	}

	public void addMovieReview(MovieReview movieReview) {
		this.movieReviews.add(movieReview);
	}

	public MovieAgeRating getMovieAgeRating() {
		return movieAgeRating;
	}

	public void setMovieAgeRating(MovieAgeRating movieAgeRating) {
		this.movieAgeRating = movieAgeRating;
	}

	public double getMovieDuration() {
		return movieDuration;
	}

	public void setMovieDuration(double movieDuration) {
		this.movieDuration = movieDuration;
	}

	public int getMovieTicketsSold() {
		return movieTicketsSold;
	}

	public void setMovieTicketsSold(int movieTicketsSold) {
		this.movieTicketsSold = movieTicketsSold;
	}

	public abstract double getMoviePrice();

	public abstract void setMoviePrice(double price);

	public abstract MovieType getMovieType();

	public abstract void setMovieType(MovieType movieType);
}
