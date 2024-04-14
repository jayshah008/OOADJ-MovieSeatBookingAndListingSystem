package src.model;

import java.util.ArrayList;
import src.database.*;
import src.model.enums.*;

public class PlatinumCinema extends Cinema {

    private double cinemaPrice;

    public PlatinumCinema(String UUID, ArrayList<Seat> seats, int numOfRows, int totalNumOfSeats) {
        super(UUID, seats, numOfRows, totalNumOfSeats);
        super.setCinemaClass(CinemaClass.PLATINUM);
        this.setCinemaPrice(Database.PRICES.getDefaultPlatinumCinemaPrice());
    }

    public void setCinemaPrice(double cinemaPrice) {
        this.cinemaPrice = cinemaPrice;
    }

    public double getCinemaPrice() {
        return this.cinemaPrice;
    }
}
