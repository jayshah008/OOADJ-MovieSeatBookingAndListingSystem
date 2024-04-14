package src.model;

import src.model.enums.*;
import java.util.*;
import src.database.*;

public class ThreeDMovie extends Movie {

    private MovieType type;

    private double moviePrice;

    public ThreeDMovie(String UUID, String movieTitle, MovieAgeRating movieAgeRating,
            MovieShowingStatus showingStatus, ArrayList<String> movieCast, String movieDirector,
            String movieSynopsis, double movieDuration) {
        super(UUID, movieTitle, movieAgeRating, showingStatus, movieCast,
                movieDirector, movieSynopsis, movieDuration);
        this.type = MovieType.ThreeD;
        this.moviePrice = Database.PRICES.getDefault3DMoviePrice();
    }

    @Override
    public void setMoviePrice(double price) {
        this.moviePrice = price;
    }

    @Override
    public double getMoviePrice() {
        return this.moviePrice;
    }

    @Override
    public MovieType getMovieType() {
        return this.type;
    }

    public void setMovieType(MovieType type) {
        this.type = type;
    }
}