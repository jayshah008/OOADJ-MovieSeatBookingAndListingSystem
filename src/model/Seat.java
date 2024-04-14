package src.model;

import java.io.Serializable;
import src.model.enums.*;

public abstract class Seat implements Serializable {

    private String UUID;

    private boolean isAssigned;
    private static final long serialVersionUID = 3L;

    public Seat(String UUID) {
        this.setUUID(UUID);
        this.isAssigned = false;
    }

    public String getUUID() {
        return this.UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public abstract SeatType getSeatType();

    public abstract void setSeatType(SeatType seatType);

    public boolean getAssignStatus() {
        return this.isAssigned;
    }

    public void setAssignStatus(boolean assign) {
        this.isAssigned = assign;
    }

    public abstract double getSeatPrice();

    public abstract void setSeatPrice(double seatPrice);
}
