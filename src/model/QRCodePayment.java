package src.model;

import src.model.enums.*;

public class QRCodePayment extends Payment {

    private PaymentType paymentType;

    private double movieTicketPrice;

    public QRCodePayment(String UUID, PaymentType paymentType, String transactionID, double movieTicketPrice) {
        super(UUID, transactionID);
        this.setMovieTicketPrice(movieTicketPrice);
        this.setPaymentType(paymentType);
    }

    public PaymentType getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public double getMovieTicketPrice() {
        return this.movieTicketPrice;
    }

    public void setMovieTicketPrice(double movieTicketPrice) {
        this.movieTicketPrice = movieTicketPrice;
    }
}
