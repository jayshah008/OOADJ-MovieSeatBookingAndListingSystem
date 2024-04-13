package src.controller;

import java.util.*;
import src.database.*;
import src.model.*;
import src.model.enums.*;

/**
 * Controller class for handling all logics related to the {@link Database}
 * 
 * @author Lee Juin
 * @version 1.0
 */
public class DatabaseManager {

    /**
     * Constructor
     */
    public DatabaseManager() {
    }

    /**
     * Method to load initial cineplex data into database
     * 
     * @return {@code true} if initialization is successful, {@code false} otherwise
     */
    public static boolean initializeCineplexData() {
        ArrayList<Cineplex> cineplexs = Database.getValueList(Database.CINEPLEX.values());

        if (cineplexs.size() != 0) {
            return false;
        }

        String cineplexName;
        String cineplexLocation;
        int numOfCinemas;
        ArrayList<Cinema> cinemas = new ArrayList<>();

        // Data 1
        cineplexName = "CATHAY CINEPLEX CAUSEWAY POINT";
        cineplexLocation = """
                1 Woodlands Square
                Level 7, Causeway Point
                Singapore 738099
                    """;
        numOfCinemas = 5;
        for (int i = 0; i < numOfCinemas; i++) {
            cinemas.add(initializeCinemaData(i % 3));
        }
        CineplexManager.createCineplex(cineplexName, numOfCinemas, cinemas, cineplexLocation);

        // Data 2
        cineplexName = "CATHAY CINEPLEX AMK HUB";
        cineplexLocation = """
                53 Ang Mo Kio Ave 3
                Level 4, AMK Hub
                Singapore 569933
                    """;
        numOfCinemas = 4;
        cinemas = new ArrayList<>();
        for (int i = 0; i < numOfCinemas; i++) {
            cinemas.add(initializeCinemaData(i % 3));
        }
        CineplexManager.createCineplex(cineplexName, numOfCinemas, cinemas, cineplexLocation);

        // Data 3
        cineplexName = "CATHAY CINEPLEX JEM";
        cineplexLocation = """
                50 Jurong Gateway Road
                Level 5, Jem
                Singapore 608549
                    """;
        numOfCinemas = 6;
        cinemas = new ArrayList<>();
        for (int i = 0; i < numOfCinemas; i++) {
            cinemas.add(initializeCinemaData(i % 3));
        }
        CineplexManager.createCineplex(cineplexName, numOfCinemas, cinemas, cineplexLocation);

        return true;
    }

    /**
     * Method to load inital cinema data into database
     * 
     * @param choice is the choice of {@link CinemaClass} to be initialized
     * @return {@link Cinema} instance created
     */
    public static Cinema initializeCinemaData(int choice) {
        CinemaClass cinemaClass;
        ArrayList<Seat> seats;

        switch (choice) {
            case 0:
                // Data 1
                cinemaClass = CinemaClass.STANDARD;
                seats = initializeSeatData(cinemaClass);
                return CinemaManager.createStandardCinema(seats);
            case 1:
                // Data 2
                cinemaClass = CinemaClass.IMAX;
                seats = initializeSeatData(cinemaClass);
                return CinemaManager.createIMaxCinema(seats);

            case 2:
                // Data 3
                cinemaClass = CinemaClass.PLATINUM;
                seats = initializeSeatData(cinemaClass);
                return CinemaManager.createPlatinumCinema(seats);
        }
        return null;
    }

    /**
     * Method to initialize the initial seat data into database
     * 
     * @param cinemaClass is the {@link CinemaClass} of the cinema the seats belong
     *                    to
     * @return ArrayList of {@link Seat} instances created
     */
    public static ArrayList<Seat> initializeSeatData(CinemaClass cinemaClass) {
        int numOfSeatsPerRow;
        ArrayList<Seat> seats = new ArrayList<>();

        if (cinemaClass != CinemaClass.PLATINUM) {
            numOfSeatsPerRow = Database.totalNumOfSeats / Database.numOfRows;

            for (int i = 0; i < Database.numOfCoupleRows; i++) {
                for (int j = 0; j < numOfSeatsPerRow; j++) {
                    seats.add(SeatManager.createCoupleSeat());
                }
            }

            for (int i = 0; i < Database.numOfRows - Database.numOfCoupleRows; i++) {
                for (int j = 0; j < numOfSeatsPerRow; j++) {
                    seats.add(SeatManager.createStandardSeat());
                }
            }
        } else {
            numOfSeatsPerRow = Database.platinumNumOfSeatsPerRow;
            int numofRows = Database.platinumNumOfRow;

            for (int i = 0; i < numofRows; i++) {
                for (int j = 0; j < numOfSeatsPerRow; j++) {
                    seats.add(SeatManager.createStandardSeat());
                }
            }

        }
        return seats;
    }

    /**
     * Method to initialize the initial movie schedule data into database
     * 
     * @return {@code true} if initialization is successful, {@code false} otherwise
     */
    public static boolean initializeMovieScheduleData() {
        ArrayList<MovieSchedule> movieSchedules = Database.getValueList(Database.MOVIE_SCHEDULE.values());

        if (movieSchedules.size() != 0) {
            return false;
        }

        Movie movieOnShow;
        ArrayList<Cinema> showingVenue;
        ArrayList<String> showingVenueUUID;
        ArrayList<ArrayList<Seat>> seatingPlan = new ArrayList<ArrayList<Seat>>();
        ArrayList<DateTime> showingTime = new ArrayList<DateTime>();
        CinemaClass cinemaClass;
        Cineplex cineplex;
        ArrayList<Cineplex> cineplexes = Database.getValueList(Database.CINEPLEX.values());
        ArrayList<Movie> movies = Database.getValueList(Database.MOVIE.values());
        Collections.sort(cineplexes);

        for (int i = 0; i < 7; i++) {

            // Data 1 (Ticket to paradise)
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(0);
            cinemaClass = CinemaClass.IMAX;
            cineplex = cineplexes.get(1);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            // Data 2 (Smile)
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(1);
            cinemaClass = CinemaClass.PLATINUM;
            cineplex = cineplexes.get(1);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));
            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(1);
            cinemaClass = CinemaClass.PLATINUM;
            cineplex = cineplexes.get(2);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));
            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(1);
            cinemaClass = CinemaClass.PLATINUM;
            cineplex = cineplexes.get(0);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            // Data 3 Faces of Anne
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(2);
            cinemaClass = CinemaClass.STANDARD;
            cineplex = cineplexes.get(1);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(2);
            cinemaClass = CinemaClass.IMAX;
            cineplex = cineplexes.get(2);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(2);
            cinemaClass = CinemaClass.STANDARD;
            cineplex = cineplexes.get(0);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            // Data 4 Phone Bhoot
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(3);
            cinemaClass = CinemaClass.IMAX;
            cineplex = cineplexes.get(1);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(3);
            cinemaClass = CinemaClass.IMAX;
            cineplex = cineplexes.get(2);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(3);
            cinemaClass = CinemaClass.IMAX;
            cineplex = cineplexes.get(0);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            // Data 5 (Prey for the Devil)
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(4);
            cinemaClass = CinemaClass.PLATINUM;
            cineplex = cineplexes.get(1);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(4);
            cinemaClass = CinemaClass.PLATINUM;
            cineplex = cineplexes.get(2);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(4);
            cinemaClass = CinemaClass.PLATINUM;
            cineplex = cineplexes.get(0);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            // Data 6(Come Back Home)
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(5);
            cinemaClass = CinemaClass.PLATINUM;
            cineplex = cineplexes.get(2);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            // Data 7(Blockbuster wakanda forever)
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(6);
            cinemaClass = CinemaClass.IMAX;
            cineplex = cineplexes.get(1);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(6);
            cinemaClass = CinemaClass.IMAX;
            cineplex = cineplexes.get(0);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            // Data 8 (Kanrata)
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(7);
            cinemaClass = CinemaClass.IMAX;
            cineplex = cineplexes.get(0);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            // Data 9 (Wakanda 3D)
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(8);
            cinemaClass = CinemaClass.STANDARD;
            cineplex = cineplexes.get(1);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(8);
            cinemaClass = CinemaClass.STANDARD;
            cineplex = cineplexes.get(2);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(8);
            cinemaClass = CinemaClass.STANDARD;
            cineplex = cineplexes.get(0);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            // Data 10 Sadako

            // Data 11 Black adam blockbuster
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(10);
            cinemaClass = CinemaClass.PLATINUM;
            cineplex = cineplexes.get(1);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(10);
            cinemaClass = CinemaClass.STANDARD;
            cineplex = cineplexes.get(2);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 16, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(10);
            cinemaClass = CinemaClass.PLATINUM;
            cineplex = cineplexes.get(0);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            // Data 12 (Ajoomma)
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(11);
            cinemaClass = CinemaClass.STANDARD;
            cineplex = cineplexes.get(2);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            // Data 13 (Riana)
            showingTime = new ArrayList<>();
            movieOnShow = movies.get(12);
            cinemaClass = CinemaClass.STANDARD;
            cineplex = cineplexes.get(1);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(12);
            cinemaClass = CinemaClass.IMAX;
            cineplex = cineplexes.get(2);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);

            showingTime = new ArrayList<>();
            movieOnShow = movies.get(12);
            cinemaClass = CinemaClass.STANDARD;
            cineplex = cineplexes.get(0);
            showingVenue = new ArrayList<>();
            showingVenueUUID = new ArrayList<>();
            seatingPlan = new ArrayList<>();
            showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                    cineplex));
            showingVenueUUID.add(showingVenue.get(0).getUUID());
            seatingPlan.add(showingVenue.get(0).duplicateSeats());
            showingTime.add(new DateTime(00, 23, 1 + i, 13 + i, 11, 2022));
            showingVenueUUID.add(showingVenue.get(1).getUUID());
            seatingPlan.add(showingVenue.get(1).duplicateSeats());
            showingTime.add(new DateTime(00, 10, 1 + i, 13 + i, 11, 2022));

            MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                    showingVenueUUID, seatingPlan, showingTime);
        }

        // for Christmas 25/12/2022
        // Data 1 (Ticket to paradise)

        // Data 2 (Smile)
        showingTime = new ArrayList<>();
        movieOnShow = movies.get(1);
        cinemaClass = CinemaClass.PLATINUM;
        cineplex = cineplexes.get(1);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));
        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(1);
        cinemaClass = CinemaClass.PLATINUM;
        cineplex = cineplexes.get(2);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));
        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(1);
        cinemaClass = CinemaClass.PLATINUM;
        cineplex = cineplexes.get(0);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        // Data 3 Faces of Anne
        showingTime = new ArrayList<>();
        movieOnShow = movies.get(2);
        cinemaClass = CinemaClass.STANDARD;
        cineplex = cineplexes.get(1);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(2);
        cinemaClass = CinemaClass.IMAX;
        cineplex = cineplexes.get(2);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(2);
        cinemaClass = CinemaClass.STANDARD;
        cineplex = cineplexes.get(0);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        // Data 4 Phone Bhoot
        showingTime = new ArrayList<>();
        movieOnShow = movies.get(3);
        cinemaClass = CinemaClass.IMAX;
        cineplex = cineplexes.get(1);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(3);
        cinemaClass = CinemaClass.IMAX;
        cineplex = cineplexes.get(2);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(3);
        cinemaClass = CinemaClass.IMAX;
        cineplex = cineplexes.get(0);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        // Data 5 (Prey for the Devil)
        showingTime = new ArrayList<>();
        movieOnShow = movies.get(4);
        cinemaClass = CinemaClass.PLATINUM;
        cineplex = cineplexes.get(1);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(4);
        cinemaClass = CinemaClass.PLATINUM;
        cineplex = cineplexes.get(2);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(4);
        cinemaClass = CinemaClass.PLATINUM;
        cineplex = cineplexes.get(0);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        // Data 6(Come Back Home)
        showingTime = new ArrayList<>();
        movieOnShow = movies.get(5);
        cinemaClass = CinemaClass.PLATINUM;
        cineplex = cineplexes.get(2);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        // Data 7(Blockbuster wakanda forever)
        showingTime = new ArrayList<>();
        movieOnShow = movies.get(6);
        cinemaClass = CinemaClass.IMAX;
        cineplex = cineplexes.get(1);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(6);
        cinemaClass = CinemaClass.IMAX;
        cineplex = cineplexes.get(0);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        // Data 8 (Kanrata)
        showingTime = new ArrayList<>();
        movieOnShow = movies.get(7);
        cinemaClass = CinemaClass.IMAX;
        cineplex = cineplexes.get(0);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        // Data 9 (Wakanda 3D)
        showingTime = new ArrayList<>();
        movieOnShow = movies.get(8);
        cinemaClass = CinemaClass.STANDARD;
        cineplex = cineplexes.get(1);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(8);
        cinemaClass = CinemaClass.STANDARD;
        cineplex = cineplexes.get(2);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(8);
        cinemaClass = CinemaClass.STANDARD;
        cineplex = cineplexes.get(0);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        // Data 10 Sadako

        // Data 11 Black adam blockbuster
        showingTime = new ArrayList<>();
        movieOnShow = movies.get(10);
        cinemaClass = CinemaClass.PLATINUM;
        cineplex = cineplexes.get(1);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(10);
        cinemaClass = CinemaClass.STANDARD;
        cineplex = cineplexes.get(2);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 16, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(10);
        cinemaClass = CinemaClass.PLATINUM;
        cineplex = cineplexes.get(0);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        // Data 12 (Ajoomma)
        showingTime = new ArrayList<>();
        movieOnShow = movies.get(11);
        cinemaClass = CinemaClass.STANDARD;
        cineplex = cineplexes.get(2);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        // Data 13 (Sadoku)
        showingTime = new ArrayList<>();
        movieOnShow = movies.get(9);
        cinemaClass = CinemaClass.STANDARD;
        cineplex = cineplexes.get(1);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(9);
        cinemaClass = CinemaClass.IMAX;
        cineplex = cineplexes.get(2);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        showingTime = new ArrayList<>();
        movieOnShow = movies.get(9);
        cinemaClass = CinemaClass.STANDARD;
        cineplex = cineplexes.get(0);
        showingVenue = new ArrayList<>();
        showingVenueUUID = new ArrayList<>();
        seatingPlan = new ArrayList<>();
        showingVenue.addAll(CinemaManager.filterCinemaByClass(cinemaClass,
                cineplex));
        showingVenueUUID.add(showingVenue.get(0).getUUID());
        seatingPlan.add(showingVenue.get(0).duplicateSeats());
        showingTime.add(new DateTime(00, 23, 1, 25, 12, 2022));
        showingVenueUUID.add(showingVenue.get(1).getUUID());
        seatingPlan.add(showingVenue.get(1).duplicateSeats());
        showingTime.add(new DateTime(00, 10, 1, 25, 12, 2022));

        MovieScheduleManager.createMovieSchedule(movieOnShow.getUUID(),
                showingVenueUUID, seatingPlan, showingTime);

        return true;

    }

    /**
     * Method to initialize the initial movie data into database
     * 
     * @return {@code true} if initialization is successful, {@code false} otherwise
     */
    public static boolean initializeMovie() {
        ArrayList<Movie> movies = Database.getValueList(Database.MOVIE.values());

        if (movies.size() != 0) {
            return false;
        }

        String title;
        MovieAgeRating movieAgeRating;
        MovieShowingStatus movieShowingStatus;
        ArrayList<String> movieCast = new ArrayList<>();
        String director;
        String synopsis;
        double duration;

        // Movie1
        title = "Marvel Studios' Black Panther: Wakanda Forever (2022)";
        movieAgeRating = MovieAgeRating.PG13;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast.add("Letitia Wright");
        movieCast.add("Tenoch Huerta");
        movieCast.add("Martin Freeman");
        movieCast.add("Lupita Nyong'o");
        movieCast.add("Danai Gurira");
        movieCast.add("Winston Duke");
        director = "Ryan Coogler";
        synopsis = "In Marvel Studios’ “Black Panther: Wakanda Forever,” Queen Ramonda (Angela Bassett), Shuri (Letitia Wright), M’Baku (Winston Duke), Okoye (Danai Gurira) and the Dora Milaje (including Florence Kasumba), fight to protect their nation from intervening world powers in the wake of King T’Challa’s death. As the Wakandans strive to embrace their next chapter, the heroes must band together with the help of War Dog Nakia (Lupita Nyong’o) and Everett Ross (Martin Freeman) and forge a new path for the kingdom of Wakanda. Introducing Tenoch Huerta as Namor, king of a hidden undersea nation, the film also stars Dominique Thorne, Michaela Coel, Mabel Cadena and Alex Livanalli. ";
        duration = 161;
        MovieManager.createBlockbusterMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie2
        title = "Marvel Studios' Black Panther: Wakanda Forever (2022)";
        movieAgeRating = MovieAgeRating.PG13;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast.add("Letitia Wright");
        movieCast.add("Tenoch Huerta");
        movieCast.add("Martin Freeman");
        movieCast.add("Lupita Nyong'o");
        movieCast.add("Danai Gurira");
        movieCast.add("Winston Duke");
        director = "Ryan Coogler";
        synopsis = "In Marvel Studios' “Black Panther: Wakanda Forever,” Queen Ramonda (Angela Bassett), Shuri (Letitia Wright), M'Baku (Winston Duke), Okoye (Danai Gurira) and the Dora Milaje (including Florence Kasumba), fight to protect their nation from intervening world powers in the wake of King T'Challa's death. As the Wakandans strive to embrace their next chapter, the heroes must band together with the help of War Dog Nakia (Lupita Nyong'o) and Everett Ross (Martin Freeman) and forge a new path for the kingdom of Wakanda. Introducing Tenoch Huerta as Namor, king of a hidden undersea nation, the film also stars Dominique Thorne, Michaela Coel, Mabel Cadena and Alex Livanalli.";
        duration = 161;
        MovieManager.createThreeDMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie3
        title = "Black Adam (2022)";
        movieAgeRating = MovieAgeRating.PG13;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast.add("Dwayne Johnson");
        movieCast.add("Aldis Hodge");
        movieCast.add("Noah Centineo");
        movieCast.add("Sarah Shahi");
        movieCast.add("Marwan Kenzari");
        movieCast.add("Quintessa Swindell");
        director = "Jaume Collet-Serra";
        synopsis = "Nearly 5,000 years after he was bestowed with the almighty powers of the Egyptian gods -- and imprisoned just as quickly -- Black Adam is freed from his earthly tomb, ready to unleash his unique form of justice on the modern world.";
        duration = 125;
        MovieManager.createBlockbusterMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie4
        title = "Ajoomma (2022)";
        movieAgeRating = MovieAgeRating.NC16;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast = new ArrayList<String>();
        movieCast.add("Hong Huifang");
        movieCast.add("Jung Dong-Hwan");
        movieCast.add("Kang Hyung Suk");
        movieCast.add("Yeo Jingoo");
        director = "He Shuming";
        synopsis = "Produced by award-winning filmmaker Anthony Chen. Auntie (Hong Huifang), is a middle-aged Singaporean woman who has dedicated the best years of her life to caring for her family. Now widowed with her grown up son, Sam (Shane Pow) about to fly the roost, Auntie is left to contend with a whole new identity beyond her roles of daughter, wife, and mother.";
        duration = 90;
        MovieManager.createStandardMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie5
        title = "Faces of Anne (2022)";
        movieAgeRating = MovieAgeRating.NC16;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast = new ArrayList<String>();
        movieCast.add("Kongdej Jaturanrasamee");
        movieCast.add("Waruntorn Paonil");
        movieCast.add("VioletteI Wautier");
        movieCast.add("Phantira Pipityakorn");
        movieCast.add("Arachaporn Pokinpakorn");
        movieCast.add("Sutatta Udomsilp");
        movieCast.add("Praewa Suthamphong");
        movieCast.add("Eisaya Hosuwan");
        movieCast.add("Sawanya Paisarnpayak");
        director = "Kongdej Jaturanrasamee";
        synopsis = "Everyone is called \"Anne\" and they are all being chased! We challenge you to prove your bravery in a movie that will awaken all your senses in order to survive. In Faces of Anne, Anne takes you on a terrifying mystery journey with secrets lurking in the dark corners. When all the girls named \"Anne\" wakes up with fuzzy memories which the doctors and nurses keep injecting and saying it is mental symptoms that they think to themselves, along with hypnosis to make them remember who they are. Not too long ago in this mental ward, every time Anne opens her eyes, every time Anne dies, or every time Anne's face changes, the young women in this place begin to mysteriously disappear. Fear gradually began to take root in the minds of the survivors. Until they encountered the legendary deer-headed demon \"Wedigo\". In order to survive, everyone must work together. The only way is to piece together the past and the mysteries that lead to the truth and to find a way to escape in time before death takes them away. ";
        duration = 116;
        MovieManager.createStandardMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie6
        title = "Phone Bhoot (2022)";
        movieAgeRating = MovieAgeRating.NC16;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast = new ArrayList<String>();
        movieCast.add("Katrina Kaif");
        movieCast.add("Siddhant Chaturvedi");
        movieCast.add("Ishaan Khatter");
        movieCast.add("Jackie Shroff");
        director = "Gurmmeet Singh";
        synopsis = "Two crazy, jobless, horror-film addicts Major and Gullu, under immense family pressure to find work, hit upon the idea to float a unique ghost-capturing service when they meet a female spirit, Ragini, who makes their business a success but in return asks them for a dangerous favour which they are compelled to honour. ";
        duration = 135;
        MovieManager.createStandardMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie7
        title = "Prey For the Devil (2022)";
        movieAgeRating = MovieAgeRating.NC16;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast = new ArrayList<String>();
        movieCast.add("Daniel Stamm");
        movieCast.add("Colin Salmon");
        movieCast.add("Virginia Madsen");
        movieCast.add("Ben Cross");
        director = "Daniel Stamm";
        synopsis = "Sister Ann (Jacqueline Byers) believes she is answering a calling to be the first female exorcist… but who, or what, called her? In response to a global rise in demonic possessions, Ann seeks out a place at an exorcism school reopened by the Catholic Church. Until now these schools have only trained priests in the Rite of Exorcism – but a professor (Colin Salmon) recognizes Sister Ann’s gifts and agrees to train her. Thrust onto the spiritual frontline with fellow student Father Dante (Christian Navarro), Sister Ann finds herself in a battle for the soul of a young girl, who Sister Ann believes is possessed by the same demon that tormented her own mother years ago. Determined to root out the evil, Ann soon realizes the Devil has her right where he wants her. ";
        duration = 93;
        MovieManager.createStandardMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie8
        title = "Come Back Home (2022)";
        movieAgeRating = MovieAgeRating.PG;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast = new ArrayList<String>();
        movieCast.add("Donnie Yen 甄子丹");
        movieCast.add("Han Xue 韩雪");
        movieCast.add("Tang Xu 唐旭");
        movieCast.add("Jia Bing 贾冰");
        director = "Lo Chi Leung 罗志良";
        synopsis = "In the cold winter, a group of Shenzhen tourist families take a trip to the northeast Changbai Mountain. It was originally intended to be a happy and harmonious holiday, but due to the negligence of his father, an 8-year-old boy is unfortunately lost. The parents seek assistance from the relevant local authorities, and the search and rescue operation begins immediately. The golden rescue time of 24 hours passes, followed by the routine safety limit of 48 hours, but the child is still nowhere to be found. Even if there is little hope, the father and the search and rescue teams will not give up… ";
        duration = 102;
        MovieManager.createStandardMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie9
        title = "The Sacred Riana 2: Bloody Mary (2022)";
        movieAgeRating = MovieAgeRating.PG13;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast = new ArrayList<String>();
        movieCast.add("The Sacred Riana");
        movieCast.add("Brooklyn Alif Rea");
        movieCast.add("Frisly Indigo");
        director = "Billy Christian";
        synopsis = "The disappearance of the Riani Doll makes Riana come to Elodia's dormitory.The dormitory is inhabited by a group of female students, who often perform game rituals using mirrors, namely Bloody Mary. However, this game leads to terror that harms the Elodia students. Riani's missing doll is accused of being the mastermind. Riana, who is unraveling the mystery, is trapped in the midst of the conditions to bring Riani home and find answers to the terror alleged to her beloved doll. ";
        duration = 113;
        MovieManager.createStandardMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie10
        title = "Ticket to Paradise (2022)";
        movieAgeRating = MovieAgeRating.PG13;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast = new ArrayList<String>();
        movieCast.add("George Clooney");
        movieCast.add("Julia Roberts");
        movieCast.add("Kaitlyn Dever");
        movieCast.add("Billie Lourd");
        director = "Ol Parker";
        synopsis = "Academy Award® winners George Clooney and Julia Roberts reunite on the big screen as exes who find themselves on a shared mission to stop their lovestruck daughter from making the same mistake they once made. From Working Title, Smokehouse Pictures and Red Om Films, Ticket to Paradise is a romantic comedy about the sweet surprise of second chances. ";
        duration = 104;
        MovieManager.createStandardMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie11
        title = "Smile (2022)";
        movieAgeRating = MovieAgeRating.M18;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast = new ArrayList<String>();
        movieCast.add("Sosie Bacon");
        movieCast.add("Jessie T. Usher");
        movieCast.add("Kyle Gallner");
        director = "Parker Finn";
        synopsis = "After witnessing a bizarre, traumatic incident involving a patient, Dr. Rose Cotter (Sosie Bacon) starts experiencing frightening occurrences that she can't explain. As an overwhelming terror begins taking over her life, Rose must confront her troubling past in order to survive and escape her horrifying new reality. ";
        duration = 115;
        MovieManager.createStandardMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie12
        title = "Kanrata (2022)";
        movieAgeRating = MovieAgeRating.NC16;
        movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
        movieCast = new ArrayList<String>();
        movieCast.add("Rishab Shetty");
        movieCast.add("Sapthami Gowda");
        movieCast.add("Kishore");
        director = "Rishab Shetty";
        synopsis = "Set in the fictional village of Dakshina Kannada, Kantara is a visual grandeur that brings alive the traditional culture of Kambla and Bhootha Kola. It is believed that Demigods are the guardians and their energies encircle the village. In the story, there is a ripple when a battle of ego swirls along the tradition and culture of the land. The soul of the story is on human and nature conflict in which Shiva is the rebellion and works against nature. There are intense conflicts he indulges in. In the end, a much-awaiting loop leads to war between the villagers and the evil forces. Will Shiva, the protagonist of the film be able to reinstate peace and harmony in the village by perceiving his existence? ";
        duration = 148;
        MovieManager.createStandardMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        // Movie13
        title = "Sadako Dx (2022)";
        movieAgeRating = MovieAgeRating.NC16;
        movieShowingStatus = MovieShowingStatus.PREVIEW;
        movieCast = new ArrayList<String>();
        movieCast.add("Fuka Koshiba");
        movieCast.add("Kazuma Kawamura");
        movieCast.add("Mario Kuroba");
        director = "Hisashi Kimura";
        synopsis = "The Ring Curse mutates and spreads. Ayaka Ichijo (Fuka Koshiba) is a graduate student with an IQ of 200 who tries to investigate the strange deaths happening nationwide after people supposedly watched a cursed video. Ayaka is sceptical about the video and the legend surrounding it, which have become a viral sensation. She soon learns that her younger sister has viewed a copy of the video out of curiosity, and her death is set to occur in 24 hours. Can Ayaka uncover the mystery behind the cursed video and save her sister? ";
        duration = 100;
        MovieManager.createStandardMovie(title, movieAgeRating, movieShowingStatus,
                movieCast, director, synopsis, duration);

        return true;
    }

    /**
     * Method to initialize the initial cinema staff data into database
     * 
     * @return {@code true} if initialization is successful, {@code false} otherwise
     */
    public static boolean initalizeCinemaStaff() {
        ArrayList<CinemaStaff> cinemaStaffs = Database.getValueList(Database.CINEMA_STAFF.values());

        if (cinemaStaffs.size() != 0) {
            return false;
        }

        String name = "CinemaStaf";
        String username = "admin";
        String password = "password";
        CinemaStaffManager.createCinemaStaff(name, password, username);

        return true;
    }

    /**
     * Method to initialize the default prices
     */
    public static void initializePrices() {
        Prices prices = new Prices(2, 30, 3,
                4, 3,
                5, 2, 1.5,
                1.5, 2, 1.5, 1.5, 1.5);
        Database.PRICES = prices;
        DatabaseManager.reloadDatabase();
    }

    /**
     * Generic helper function to save written changes into the database
     * 
     * @param <K>    is the generic form for key
     * @param <V>    is the generic form for value
     * @param UUID   is the unique ID of the object to be updated
     * @param object is the object to be updated
     * @param data   is the HashMap database to be updated
     */
    public static <K, V> void saveUpdateToDatabase(K UUID, V object, HashMap<K, V> data) {
        data.put(UUID, object);
        DatabaseManager.reloadDatabase();
    }

    /**
     * Helper function to reset the database
     */
    public static void resetDatabase() {
        Database.resetDatabase();
    }

    /**
     * Helper function to reload the database. Useful upon saved changes
     */
    public static void reloadDatabase() {
        Database.writeToDatabase();
        Database.remountDatabase();
    }

    /**
     * Similar to saveUpdateToDatabase, except save to buffer instead.
     * Useful for updating large data
     * 
     * @param <K>    is the generic form for key
     * @param <V>    is the generic form for value
     * @param UUID   is the unique ID of the object to be updated
     * @param object is the object to be updated
     * @param data   is the HashMap database to be updated
     */
    public static <K, V> void saveUpdateToBuffer(K UUID, V object, HashMap<K, V> data) {
        data.put(UUID, object);
    }
}
