package src.model;

import java.util.*;
import src.database.Database;
import src.model.enums.*;

public class StandardMovie extends Movie {

    private MovieType type;

    private double moviePrice;

    public StandardMovie(String UUID, String movieTitle, MovieAgeRating movieAgeRating,
            MovieShowingStatus showingStatus, ArrayList<String> movieCast, String movieDirector, String movieSynopsis,
            double movieDuration) {
        super(UUID, movieTitle, movieAgeRating, showingStatus, movieCast,
                movieDirector, movieSynopsis, movieDuration);
        this.type = MovieType.Standard;
        this.moviePrice = Database.PRICES.getDefaultStandardMoviePrice();
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