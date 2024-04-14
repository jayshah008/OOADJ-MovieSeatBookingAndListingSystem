package src.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Cineplex implements Comparable, Serializable {

    private String UUID;

    private String cineplexName;

    private String cineplexLocation;

    private int numOfCinemas;

    private ArrayList<Cinema> cinemas;
    private static final long serialVersionUID = 2L;

    public Cineplex(String UUID, String cineplexName, int numOfCinemas, ArrayList<Cinema> cinemas,
            String cineplexLocation) {
        this.setUUID(UUID);
        this.setCineplexName(cineplexName);
        this.setNumOfCinemas(numOfCinemas);
        this.setCinemas(cinemas);
        this.setCineplexLocation(cineplexLocation);
    }

    public String getUUID() {
        return this.UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getCineplexName() {
        return this.cineplexName;
    }

    public void setCineplexName(String cineplexName) {
        this.cineplexName = cineplexName;
    }

    public int getNumOfCinemas() {
        return this.numOfCinemas;
    }

    public void setNumOfCinemas(int numOfCinemas) {
        this.numOfCinemas = numOfCinemas;
    }

    public ArrayList<Cinema> getCinemas() {
        return this.cinemas;
    }

    public void setCinemas(ArrayList<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public void addCinema(Cinema cinema) {
        this.cinemas.add(cinema);
        this.numOfCinemas++;
    }

    @Override
    public int compareTo(Object object) {
        Cineplex cineplex = (Cineplex) object;
        return this.getUUID().compareTo(cineplex.getUUID());
    }

    public String getCineplexLocation() {
        return this.cineplexLocation;
    }

    public void setCineplexLocation(String cineplexLocation) {
        this.cineplexLocation = cineplexLocation;
    }
}
