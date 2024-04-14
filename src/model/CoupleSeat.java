package src.model;

import src.model.enums.*;
import src.database.*;

public class CoupleSeat extends Seat {

    private double seatPrice;

    private SeatType seatType;

    public CoupleSeat(String UUID) {
        super(UUID);
        this.setSeatPrice(Database.PRICES.getDefaultSeatPrice());
        this.setSeatType(SeatType.COUPLE);
    }

    public void setSeatPrice(double seatPrice) {
        this.seatPrice = seatPrice;
    }

    public double getSeatPrice() {
        return this.seatPrice;
    }

    public SeatType getSeatType() {
        return this.seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
}
