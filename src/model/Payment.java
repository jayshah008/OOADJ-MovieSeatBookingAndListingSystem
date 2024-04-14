package src.model;

import java.io.Serializable;

import src.model.enums.*;

public abstract class Payment implements Serializable {
    private String UUID;

    private String transactionID;

    private static final long serialVersionUID = 16L;

    public Payment(String UUID, String transactionID) {
        this.setUUID(UUID);
        this.setTransactionID(transactionID);
    }

    public String getUUID() {
        return this.UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public abstract PaymentType getPaymentType();

    public abstract void setPaymentType(PaymentType paymentType);

    public String getTransactionID() {
        return this.transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public abstract double getMovieTicketPrice();

    public abstract void setMovieTicketPrice(double movieTicketPrice);
}
