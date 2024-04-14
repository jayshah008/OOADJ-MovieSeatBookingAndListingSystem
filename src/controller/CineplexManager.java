package src.controller;

import java.util.*;
import src.model.*;
import src.database.*;
import src.handler.*;


public class CineplexManager {


    public CineplexManager() {
    }


    public static Cineplex createCineplex(String cineplexName, int numOfCinemas, ArrayList<Cinema> cinemas,
            String cineplexLocation) {
        String UUID = String.format("CP%04d", DatabaseHandler.generateUUID(Database.CINEPLEX));
        Cineplex cineplex = new Cineplex(UUID, cineplexName, numOfCinemas, cinemas, cineplexLocation);
        DatabaseManager.saveUpdateToDatabase(UUID, cineplex, Database.CINEPLEX);
        return cineplex;
    }


    public static Cineplex getCineplexByCinema(Cinema targetCinema) {
        ArrayList<Cineplex> cineplexes = Database.getValueList(Database.CINEPLEX.values());

        for (int i = 0; i < cineplexes.size(); i++) {
            Cineplex cineplex = cineplexes.get(i);
            for (int j = 0; j < cineplex.getCinemas().size(); j++) {
                Cinema cinema = cineplex.getCinemas().get(j);
                if (cinema.getUUID().equals(targetCinema.getUUID())) {
                    return cineplex;
                }
            }
        }
        return null;
    }


    public static ArrayList<Cineplex> filterCineplexesByMovie(Movie movie) {
        ArrayList<Cineplex> cineplexes = new ArrayList<>();
        MovieSchedule movieSchedule = MovieScheduleManager.getMovieScheduleByMovie(movie);
        cineplexes.addAll(CineplexManager.filterCineplexesByMovieSchedule(movieSchedule));

        HashSet<Cineplex> cineplexesSet = new HashSet<Cineplex>(cineplexes);
        ArrayList<Cineplex> finalCineplexes = new ArrayList<>(cineplexesSet);
        return finalCineplexes;
    }


    public static ArrayList<Cineplex> filterCineplexesByMovieSchedule(MovieSchedule movieSchedule) {
        ArrayList<Cineplex> cineplexes = new ArrayList<>();
        ArrayList<Cineplex> allCineplexes = Database.getValueList(Database.CINEPLEX.values());
        ArrayList<String> targetCinemasUUID = movieSchedule.getShowingVenues();

        for (int i = 0; i < allCineplexes.size(); i++) {
            Cineplex cineplex = allCineplexes.get(i);
            ArrayList<Cinema> cineplexCinemas = cineplex.getCinemas();

            for (int j = 0; j < cineplexCinemas.size(); j++) {
                Cinema cinema = cineplexCinemas.get(j);

                if (cineplexes.indexOf(cineplex) == -1) {
                    for (int k = 0; k < targetCinemasUUID.size(); k++) {
                        String targetCinemaUUID = targetCinemasUUID.get(k);
                        if (cinema.getUUID().equals(targetCinemaUUID)) {
                            cineplexes.add(cineplex);
                        }
                    }
                }
            }
        }
        return cineplexes;
    }
}
