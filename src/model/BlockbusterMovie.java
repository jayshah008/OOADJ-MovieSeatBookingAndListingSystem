package src.model;

import java.util.*;
import src.database.Database;
import src.model.enums.*;

public class BlockbusterMovie extends Movie {

    private MovieType type;

    private double moviePrice;

    public BlockbusterMovie(String UUID, String movieTitle, MovieAgeRating movieAgeRating,
            MovieShowingStatus showingStatus, ArrayList<String> movieCast, String movieDirector, String movieSynopsis,
            double movieDuration) {
        super(UUID, movieTitle, movieAgeRating, showingStatus, movieCast, movieDirector, movieSynopsis,
                movieDuration);
        this.type = MovieType.Blockbuster;
        this.moviePrice = Database.PRICES.getDefaultBlockbusterMoviePrice();
    }

    public MovieType getMovieType() {
        return this.type;
    }

    @Override
    public double getMoviePrice() {
        return this.moviePrice;
    }

    @Override
    public void setMoviePrice(double price) {
        this.moviePrice = price;
    }

    public void setMovieType(MovieType type) {
        this.type = type;
    }

}