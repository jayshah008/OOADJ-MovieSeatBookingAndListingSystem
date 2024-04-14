package src.controller;

import java.util.*;
import src.model.*;
import src.model.enums.*;
import src.handler.*;
import src.database.*;

public class MovieManager {

    public MovieManager() {
    }

    public static Movie createStandardMovie(String movieTitle, MovieAgeRating movieAgeRating,
            MovieShowingStatus showingStatus, ArrayList<String> movieCast, String movieDirector, String movieSynopsis,
            double movieDuration) {
        String UUID = String.format("MV%03d", DatabaseHandler.generateUUID(Database.MOVIE));
        Movie movie = new StandardMovie(UUID, movieTitle, movieAgeRating, showingStatus,
                movieCast, movieDirector, movieSynopsis, movieDuration);
        DatabaseManager.saveUpdateToDatabase(UUID, movie, Database.MOVIE);
        return movie;
    }


    public static Movie createBlockbusterMovie(String movieTitle, MovieAgeRating movieAgeRating,
            MovieShowingStatus showingStatus, ArrayList<String> movieCast, String movieDirector, String movieSynopsis,
            double movieDuration) {
        String UUID = String.format("MV%03d", DatabaseHandler.generateUUID(Database.MOVIE));
        Movie movie = new BlockbusterMovie(UUID, movieTitle, movieAgeRating, showingStatus,
                movieCast, movieDirector, movieSynopsis, movieDuration);
        DatabaseManager.saveUpdateToDatabase(UUID, movie, Database.MOVIE);
        return movie;
    }


    public static Movie createThreeDMovie(String movieTitle, MovieAgeRating movieAgeRating,
            MovieShowingStatus showingStatus, ArrayList<String> movieCast, String movieDirector, String movieSynopsis,
            double movieDuration) {
        String UUID = String.format("MV%03d", DatabaseHandler.generateUUID(Database.MOVIE));
        Movie movie = new ThreeDMovie(UUID, movieTitle, movieAgeRating, showingStatus,
                movieCast, movieDirector, movieSynopsis, movieDuration);
        DatabaseManager.saveUpdateToDatabase(UUID, movie, Database.MOVIE);
        return movie;
    }


    public static void updateMovieTicketSold(Movie movie, int numOfTickets) {
        int ticket = movie.getMovieTicketsSold();
        ticket += numOfTickets;
        movie.setMovieTicketsSold(ticket);
        String UUID = movie.getUUID();
        DatabaseManager.saveUpdateToDatabase(UUID, movie, Database.MOVIE);
    }


    public static void calculateOverallReviewRating(Movie movie) {
        if (movie.getMovieReviews().size() == 0) {
            movie.setMovieOverallReviewRating(0.0);
            return;
        }
        double overallRating = 0;
        for (int i = 0; i < movie.getMovieReviews().size(); i++) {
            overallRating += movie.getMovieReviews().get(i).getMovieReviewRating();
        }
        movie.setMovieOverallReviewRating(overallRating / movie.getMovieReviews().size());
        String UUID = movie.getUUID();
        DatabaseManager.saveUpdateToDatabase(UUID, movie, Database.MOVIE);
    }

    public static ArrayList<Movie> getAllMovieList(Object user) {
        if (user instanceof CinemaStaff) {
            return Database.getValueList(Database.MOVIE.values());
        } else {
            ArrayList<Movie> filteredMovie = new ArrayList<>();
            ArrayList<Movie> allMovies = Database.getValueList(Database.MOVIE.values());
            for (int i = 0; i < allMovies.size(); i++) {
                if (allMovies.get(i).getMovieShowingStatus() != MovieShowingStatus.END_OF_SHOWING) {
                    boolean flag = false;
                    for (int j = 0; j < filteredMovie.size(); j++) {
                        if (allMovies.get(i).getMovieTitle().equals(filteredMovie.get(j).getMovieTitle())) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        filteredMovie.add(allMovies.get(i));
                    }
                }
            }
            return filteredMovie;
        }
    }

  
    public static ArrayList<Movie> getMovieList(String movieTitle) {
        ArrayList<Movie> movies = new ArrayList<>();
        ArrayList<MovieSchedule> movieSchedules = Database.getValueList(Database.MOVIE_SCHEDULE.values());

        for (int i = 0; i < movieSchedules.size(); i++) {
            MovieSchedule movieSchedule = movieSchedules.get(i);
            Movie movie = MovieManager.getMovieByUUID(movieSchedule.getMovieOnShow());
            if (movie.getMovieTitle().equals(movieTitle)
                    && movie.getMovieShowingStatus() != MovieShowingStatus.END_OF_SHOWING) {
                movies.add(movie);
            }
        }
        return movies;
    }


    public static ArrayList<Movie> sortMovie(ArrayList<Movie> movies, String sortBy) {

        for (int i = 1; i < movies.size(); i++) {
            for (int j = i; j > 0; j--) {
                Movie movie1 = movies.get(j);
                Movie movie2 = movies.get(j - 1);
                if (sortBy.equals("ratings")) {
                    if (movie1.getMovieOverallReviewRating() > movie2.getMovieOverallReviewRating()
                            || movie2.getMovieReviews().size() <= 1) {
                        Collections.swap(movies, j, j - 1);
                    } else {
                        break;
                    }
                } else {
                    if (movie1.getMovieTicketsSold() > movie2.getMovieTicketsSold()) {
                        Collections.swap(movies, j, j - 1);
                    } else {
                        break;
                    }
                }
            }
        }
        return movies;
    }

    public static boolean movieBookable(Movie movie) {
        MovieShowingStatus movieShowingStatus = movie.getMovieShowingStatus();
        if (movieShowingStatus == MovieShowingStatus.COMING_SOON ||
                movieShowingStatus == MovieShowingStatus.END_OF_SHOWING) {
            return false;
        }
        return true;
    }


    public static Movie getMovieByUUID(String movieUUID) {
        ArrayList<Movie> movies = Database.getValueList(Database.MOVIE.values());

        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie.getUUID().equals(movieUUID)) {
                return movie;
            }
        }
        return null;
    }
}