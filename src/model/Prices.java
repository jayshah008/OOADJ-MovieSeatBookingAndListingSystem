package src.model;

import java.io.Serializable;
import java.util.*;

import src.controller.*;
import src.database.*;
import src.model.enums.*;

public class Prices implements Serializable {

    private double defaultStandardCinemaPrice;

    private double defaultPlatinumCinemaPrice;

    private double defaultIMaxCinemaPrice;

    private double defaultSeatPrice;

    private double defaultBlockbusterMoviePrice;

    private double default3DMoviePrice;

    private double defaultStandardMoviePrice;

    private double defaultChildPrice;

    private double defaultStudentPrice;

    private double defaultAdultPrice;

    private double defaultSeniorCitizenPrice;

    private double holidayPrice;

    private double weekendPrice;
    private final static long serialVersionUID = 15L;

    public Prices() {
    }

    public Prices(double defaultStandardCinemaPrice,
            double defautltPlatinumCinemaPrice,
            double defaultIMaxCinemaPrice,
            double defaultSeatPrice,
            double defaultBlockbusterMoviePrice,
            double default3DMoviePrice,
            double defaultStandardMoviePrice,
            double defaultChildPrice,
            double defaultStudentPrice,
            double defaultAdultPrice,
            double defaultSeniorCitizenPrice,
            double holidayPrice,
            double weekendPrice) {

        this.setDefault3DMoviePrice(default3DMoviePrice);
        this.setDefaultAdultPrice(defaultAdultPrice);
        this.setDefaultBlockbusterMoviePrice(defaultBlockbusterMoviePrice);
        this.setDefaultChildPrice(defaultChildPrice);
        this.setDefaultIMaxCinemaPrice(defaultIMaxCinemaPrice);
        this.setDefaultPlatinumCinemaPrice(defautltPlatinumCinemaPrice);
        this.setDefaultSeatPrice(defaultSeatPrice);
        this.setDefaultSeniorCitizenPrice(defaultSeniorCitizenPrice);
        this.setDefaultStandardCinemaPrice(defaultStandardCinemaPrice);
        this.setDefaultStandardMoviePrice(defaultStandardMoviePrice);
        this.setDefaultStudentPrice(defaultStudentPrice);
        this.setHolidayPrice(holidayPrice);
        this.setWeekendPrice(weekendPrice);
    }

    public double getDefaultStandardCinemaPrice() {
        return this.defaultStandardCinemaPrice;
    }

    public double getDefaultIMaxCinemaPrice() {
        return this.defaultIMaxCinemaPrice;
    }

    public double getDefaultPlatinumCinemaPrice() {
        return this.defaultPlatinumCinemaPrice;
    }

    public void setDefaultStandardCinemaPrice(double defaultStandardCinemaPrice) {
        this.defaultStandardCinemaPrice = defaultStandardCinemaPrice;
        ArrayList<Cineplex> cineplexs = Database.getValueList(Database.CINEPLEX.values());
        for (int i = 0; i < cineplexs.size(); i++) {
            Cineplex cineplex = cineplexs.get(i);
            for (int j = 0; j < cineplex.getCinemas().size(); j++) {
                Cinema cinema = cineplex.getCinemas().get(j);
                if (cinema.getCinemaClass() == CinemaClass.STANDARD) {
                    cinema.setCinemaPrice(defaultStandardCinemaPrice);
                }
                DatabaseManager.saveUpdateToBuffer(cinema.getUUID(), cinema, Database.CINEMA);
            }
            DatabaseManager.saveUpdateToBuffer(cineplex.getUUID(), cineplex, Database.CINEPLEX);
        }
    }

    public void setDefaultIMaxCinemaPrice(double defaultIMaxCinemaPrice) {
        this.defaultIMaxCinemaPrice = defaultIMaxCinemaPrice;
        ArrayList<Cineplex> cineplexs = Database.getValueList(Database.CINEPLEX.values());
        for (int i = 0; i < cineplexs.size(); i++) {
            Cineplex cineplex = cineplexs.get(i);
            for (int j = 0; j < cineplex.getCinemas().size(); j++) {
                Cinema cinema = cineplex.getCinemas().get(j);
                if (cinema.getCinemaClass() == CinemaClass.IMAX) {
                    cinema.setCinemaPrice(defaultIMaxCinemaPrice);
                }
                DatabaseManager.saveUpdateToBuffer(cinema.getUUID(), cinema, Database.CINEMA);
            }
            DatabaseManager.saveUpdateToBuffer(cineplex.getUUID(), cineplex, Database.CINEPLEX);
        }
    }

    public void setDefaultPlatinumCinemaPrice(double defaultPlatinumCinemaPrice) {
        this.defaultPlatinumCinemaPrice = defaultPlatinumCinemaPrice;
        ArrayList<Cineplex> cineplexs = Database.getValueList(Database.CINEPLEX.values());
        for (int i = 0; i < cineplexs.size(); i++) {
            Cineplex cineplex = cineplexs.get(i);
            for (int j = 0; j < cineplex.getCinemas().size(); j++) {
                Cinema cinema = cineplex.getCinemas().get(j);
                if (cinema.getCinemaClass() == CinemaClass.PLATINUM) {
                    cinema.setCinemaPrice(defaultPlatinumCinemaPrice);
                }
                DatabaseManager.saveUpdateToBuffer(cinema.getUUID(), cinema, Database.CINEMA);
            }
            DatabaseManager.saveUpdateToBuffer(cineplex.getUUID(), cineplex, Database.CINEPLEX);
        }
    }

    public double getDefaultSeatPrice() {
        return this.defaultSeatPrice;
    }

    public void setDefaultSeatPrice(double defaultSeatPrice) {
        this.defaultSeatPrice = defaultSeatPrice;
        ArrayList<Cineplex> cineplexs = Database.getValueList(Database.CINEPLEX.values());
        for (int i = 0; i < cineplexs.size(); i++) {
            Cineplex cineplex = cineplexs.get(i);
            for (int j = 0; j < cineplex.getCinemas().size(); j++) {
                Cinema cinema = cineplex.getCinemas().get(j);
                for (int k = 0; k < cinema.getSeats().size(); k++) {
                    Seat seat = cinema.getSeats().get(k);
                    seat.setSeatPrice(defaultSeatPrice);
                    DatabaseManager.saveUpdateToBuffer(seat.getUUID(), seat, Database.SEAT);
                }
                DatabaseManager.saveUpdateToBuffer(cinema.getUUID(), cinema, Database.CINEMA);
            }
            DatabaseManager.saveUpdateToBuffer(cineplex.getUUID(), cineplex, Database.CINEPLEX);
        }
    }

    public double getDefaultBlockbusterMoviePrice() {
        return this.defaultBlockbusterMoviePrice;
    }

    public double getDefault3DMoviePrice() {
        return this.default3DMoviePrice;
    }

    public double getDefaultStandardMoviePrice() {
        return this.defaultStandardMoviePrice;
    }

    public void setDefaultBlockbusterMoviePrice(double defaultBlockbusterMoviePrice) {
        this.defaultBlockbusterMoviePrice = defaultBlockbusterMoviePrice;
        ArrayList<Movie> movies = Database.getValueList(Database.MOVIE.values());
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie.getMovieType() == MovieType.Blockbuster) {
                movie.setMoviePrice(defaultBlockbusterMoviePrice);
                DatabaseManager.saveUpdateToBuffer(movie.getUUID(), movie, Database.MOVIE);
            }
        }
    }

    public void setDefault3DMoviePrice(double default3DMoviePrice) {
        this.default3DMoviePrice = default3DMoviePrice;
        ArrayList<Movie> movies = Database.getValueList(Database.MOVIE.values());
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie.getMovieType() == MovieType.ThreeD) {
                movie.setMoviePrice(default3DMoviePrice);
                DatabaseManager.saveUpdateToBuffer(movie.getUUID(), movie, Database.MOVIE);
            }
        }
    }

    public void setDefaultStandardMoviePrice(double defaultStandardMoviePrice) {
        this.defaultStandardMoviePrice = defaultStandardMoviePrice;
        ArrayList<Movie> movies = Database.getValueList(Database.MOVIE.values());
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie.getMovieType() == MovieType.Standard) {
                movie.setMoviePrice(defaultStandardMoviePrice);
                DatabaseManager.saveUpdateToBuffer(movie.getUUID(), movie, Database.MOVIE);
            }
        }
    }

    public double getDefaultChildPrice() {
        return this.defaultChildPrice;
    }

    public double getDefaultStudentPrice() {
        return this.defaultStudentPrice;
    }

    public double getDefaultAdultPrice() {
        return this.defaultAdultPrice;
    }

    public double getDefaultSeniorCitizenPrice() {
        return this.defaultSeniorCitizenPrice;
    }

    public void setDefaultChildPrice(double defaultChildPrice) {
        this.defaultChildPrice = defaultChildPrice;
        ArrayList<MovieGoer> movieGoers = Database.getValueList(Database.MOVIE_GOER.values());
        for (int i = 0; i < movieGoers.size(); i++) {
            MovieGoer movieGoer = movieGoers.get(i);
            if (movieGoer.getMovieGoerAge() == MovieGoerAge.Child) {
                movieGoer.setGoerPrice(defaultChildPrice);
                DatabaseManager.saveUpdateToBuffer(movieGoer.getUUID(), movieGoer, Database.MOVIE_GOER);
            }
        }
    }

    public void setDefaultStudentPrice(double defaultStudentPrice) {
        this.defaultStudentPrice = defaultStudentPrice;
        ArrayList<MovieGoer> movieGoers = Database.getValueList(Database.MOVIE_GOER.values());
        for (int i = 0; i < movieGoers.size(); i++) {
            MovieGoer movieGoer = movieGoers.get(i);
            if (movieGoer.getMovieGoerAge() == MovieGoerAge.Student) {
                movieGoer.setGoerPrice(defaultStudentPrice);
                DatabaseManager.saveUpdateToBuffer(movieGoer.getUUID(), movieGoer, Database.MOVIE_GOER);
            }
        }
    }

    public void setDefaultAdultPrice(double defaultAdultPrice) {
        this.defaultAdultPrice = defaultAdultPrice;
        ArrayList<MovieGoer> movieGoers = Database.getValueList(Database.MOVIE_GOER.values());
        for (int i = 0; i < movieGoers.size(); i++) {
            MovieGoer movieGoer = movieGoers.get(i);
            if (movieGoer.getMovieGoerAge() == MovieGoerAge.Adult) {
                movieGoer.setGoerPrice(defaultAdultPrice);
                DatabaseManager.saveUpdateToBuffer(movieGoer.getUUID(), movieGoer, Database.MOVIE_GOER);
            }
        }
    }

    public void setDefaultSeniorCitizenPrice(double defaultSeniorCitizenPrice) {
        this.defaultSeniorCitizenPrice = defaultSeniorCitizenPrice;
        ArrayList<MovieGoer> movieGoers = Database.getValueList(Database.MOVIE_GOER.values());
        for (int i = 0; i < movieGoers.size(); i++) {
            MovieGoer movieGoer = movieGoers.get(i);
            if (movieGoer.getMovieGoerAge() == MovieGoerAge.SeniorCitizen) {
                movieGoer.setGoerPrice(defaultSeniorCitizenPrice);
                DatabaseManager.saveUpdateToBuffer(movieGoer.getUUID(), movieGoer, Database.MOVIE_GOER);
            }
        }
    }

    public double getHolidayPrice() {
        return this.holidayPrice;
    }

    public double getWeekendPrice() {
        return this.weekendPrice;
    }

    public void setHolidayPrice(double holidayPrice) {
        this.holidayPrice = holidayPrice;
    }

    public void setWeekendPrice(double weekendPrice) {
        this.weekendPrice = weekendPrice;
    }
}
