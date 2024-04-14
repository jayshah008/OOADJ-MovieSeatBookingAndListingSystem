package src.model;

import java.util.ArrayList;
import src.database.*;
import src.model.enums.*;

public class StandardCinema extends Cinema {

    private double cinemaPrice;

    public StandardCinema(String UUID, ArrayList<Seat> seats, int numOfRows, int totalNumOfSeats) {
        super(UUID, seats, numOfRows, totalNumOfSeats);
        this.setCinemaClass(CinemaClass.STANDARD);
        this.setCinemaPrice(Database.PRICES.getDefaultStandardCinemaPrice());
    }

    public void setCinemaPrice(double cinemaPrice) {
        this.cinemaPrice = cinemaPrice;
    }

    public double getCinemaPrice() {
        return this.cinemaPrice;
    }
}
