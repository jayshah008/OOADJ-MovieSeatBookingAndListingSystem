package src.model;

import java.util.ArrayList;
import src.model.enums.*;
import src.database.*;

public class IMaxCinema extends Cinema {

    private double cinemaPrice;

    public IMaxCinema(String UUID, ArrayList<Seat> seats, int numOfRows, int totalNumOfSeats) {
        super(UUID, seats, numOfRows, totalNumOfSeats);
        super.setCinemaClass(CinemaClass.IMAX);
        this.setCinemaPrice(Database.PRICES.getDefaultIMaxCinemaPrice());
    }

    public void setCinemaPrice(double cinemaPrice) {
        this.cinemaPrice = cinemaPrice;
    }

    public double getCinemaPrice() {
        return this.cinemaPrice;
    }
}