package src.model;

import java.util.*;
import java.io.Serializable;
import src.model.enums.*;

public abstract class Cinema implements Comparable, Serializable {

    private String UUID;

    private CinemaClass cinemaClass;

    private ArrayList<Seat> seats;

    private int numOfRows;

    private int totalNumOfSeats;
    private static final long serialVersionUID = 1L;

    public Cinema(String UUID, ArrayList<Seat> seats, int numOfRows, int totalNumOfSeats) {
        this.setUUID(UUID);
        this.setSeats(seats);
        this.setNumOfRows(numOfRows);
        this.setTotalNumOfSeats(totalNumOfSeats);
    }

    public String getUUID() {
        return this.UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public CinemaClass getCinemaClass() {
        return this.cinemaClass;
    }

    public void setCinemaClass(CinemaClass cinemaClass) {
        this.cinemaClass = cinemaClass;
    }

    public ArrayList<Seat> getSeats() {
        return this.seats;
    }

    public ArrayList<Seat> duplicateSeats() {
        ArrayList<Seat> seats = new ArrayList<>();
        seats = this.seats;
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
        this.totalNumOfSeats++;
    }

    public abstract double getCinemaPrice();

    public abstract void setCinemaPrice(double cinemaPrice);

    public int getNumOfRows() {
        return this.numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getTotalNumOfSeats() {
        return this.totalNumOfSeats;
    }

    public void setTotalNumOfSeats(int totalNumOfSeats) {
        this.totalNumOfSeats = totalNumOfSeats;
    }

    @Override
    public int compareTo(Object object) {
        Cinema cinema = (Cinema) object;
        return this.getUUID().compareTo(cinema.getUUID());
    }
}
