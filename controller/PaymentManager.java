package src.controller;

import src.database.*;
import src.handler.*;
import src.model.*;
import src.model.enums.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Controller for handling all logic related to {@link Payment}
 * 
 * @author Ci Hui
 * @version 1.0
 */

public class PaymentManager {

    /**
     * Constructor
     */
    public PaymentManager() {
    }

    /**
     * Method to create a {@link Payment} instance with Payment Type of QR Code
     * and save to database
     * 
     * @param transactionID    is the transaction ID of Payment generated when
     *                         payment is made by Movie Goer
     * @param movieTicketPrice is the total price of the Movie Tickets booked by
     *                         Movie Goer
     * @return the created {@link Payment} instance
     */
    public static Payment createQRCodePayment(String transactionID, double movieTicketPrice) {
        String UUID = String.format("PY%04d", DatabaseHandler.generateUUID(Database.PAYMENT));
        Payment payment = new QRCodePayment(UUID, PaymentType.QRCODE, transactionID, movieTicketPrice);
        DatabaseManager.saveUpdateToDatabase(UUID, payment, Database.PAYMENT);
        return payment;
    }

    /**
     * Method to create a {@link Payment} instance with Payment Type of Bank
     * Transaction
     * and save to database
     * 
     * @param transactionID    is the transaction ID of Payment generated when
     *                         payment is made by Movie Goer
     * @param movieTicketPrice is the total price of the Movie Tickets booked by
     *                         Movie Goer
     * @return the created {@link Payment} instance
     */
    public static Payment createBankTransactioPayment(String transactionID, double movieTicketPrice) {
        String UUID = String.format("PY%04d", DatabaseHandler.generateUUID(Database.PAYMENT));
        Payment payment = new BankTransactionPayment(UUID, PaymentType.BANK_TRANSACTION, transactionID,
                movieTicketPrice);
        DatabaseManager.saveUpdateToDatabase(UUID, payment, Database.PAYMENT);
        return payment;
    }

    /**
     * Method to create a {@link Payment} instance with Payment Type of Card Payment
     * and save to database
     * 
     * @param transactionID    is the transaction ID of Payment generated when
     *                         payment is made by Movie Goer
     * @param movieTicketPrice is the total price of the Movie Tickets booked by
     *                         Movie Goer
     * @return the created {@link Payment} instance
     */
    public static Payment createCardPayment(String transactionID, double movieTicketPrice) {
        String UUID = String.format("PY%04d", DatabaseHandler.generateUUID(Database.PAYMENT));
        Payment payment = new CardPayment(UUID, PaymentType.CARD_PAYMENT, transactionID, movieTicketPrice);
        DatabaseManager.saveUpdateToDatabase(UUID, payment, Database.PAYMENT);
        return payment;
    }

    /**
     * Method to calculate the price of a Movie Ticket
     * 
     * @param movieGoer   is the {@link MovieGoer} who booked the Movie Ticket
     * @param cinemaUUID  is the unique ID of the Cinema chosen by the Movie Goer
     * @param movieUUID   is the unique ID of the Movie chosen by the Movie Goer
     * @param seatUUID    is the unique ID of the Seat chosen by the Movie Goer
     * @param showingTime is the {@link DateTime} of Showing Time of the Movie
     *                    chosen by the Movie Goer
     * @return the calculated price of the Movie Ticket
     */
    public static double calculateMovieTicketPrice(MovieGoer movieGoer, String cinemaUUID, String movieUUID,
            String seatUUID, DateTime showingTime) {
        double totalPrice = 0;

        Cinema cinema = CinemaManager.getCinemaByUUID(cinemaUUID);
        Movie movie = MovieManager.getMovieByUUID(movieUUID);
        Seat seat = SeatManager.getSeatByUUID(seatUUID);
        totalPrice += movieGoer.getGoerPrice();
        totalPrice += cinema.getCinemaPrice();
        totalPrice += movie.getMoviePrice();
        totalPrice += seat.getSeatPrice();

        totalPrice *= PaymentManager.calculateHolidayWeekendPrice(showingTime);

        return totalPrice;
    }

    /**
     * Method to generate Transaction ID of the Payment
     * Format of Transaction ID: XXXYYYYMMDDhhmm
     * (XXX: Cinema Code in letters, YYYY: year, MM: month, DD: day, hh: hour, mm:
     * minutes)
     * 
     * @param cinemaCode is the Cinema Code of the chosen Cinema,
     *                   obtained from the last 3 characters of the Cinema UUID
     * @return the Transaction ID
     */
    public static String generateTransactionId(String cinemaCode) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime now = LocalDateTime.now();
        String transactionId = cinemaCode + dtf.format(now);
        return transactionId;
    }

    /**
     * Method to obtain a Payment by its UUID
     * 
     * @param paymentUUID is the target Payment's UUID
     * @return the target {@link Payment}
     */
    public static Payment getPaymentByUUID(String paymentUUID) {
        ArrayList<Payment> payments = Database.getValueList(Database.PAYMENT.values());

        for (int i = 0; i < payments.size(); i++) {
            Payment payment = payments.get(i);
            if (payment.getUUID().equals(paymentUUID)) {
                return payment;
            }
        }
        return null;
    }

    /**
     * Method to calculate the Price Weight when the day is a special day
     * ie. Weekends or Holidays
     * 
     * @param showingTime is the {@link DateTime} of the Showing Time of the Movie
     *                    chosen by the Movie Goer
     * @return the Price Weight of Special Day (Weekend or Holiday)
     */
    public static double calculateHolidayWeekendPrice(DateTime showingTime) {
        double weight = 1;

        int showingDate = showingTime.getDate();
        int showingMonth = showingTime.getMonth();
        int showingDay = showingTime.getDay();
        int showingYear = showingTime.getYear();

        ArrayList<DateTime> holidays = Database.holidays;

        for (int i = 0; i < holidays.size(); i++) {
            DateTime holiday = holidays.get(i);
            if (showingDate == holiday.getDate() && showingMonth == holiday.getMonth()
                    && showingYear == holiday.getYear()) {
                weight = Database.PRICES.getHolidayPrice();
            }
        }

        if (showingDay == 7 || showingDay == 1) {
            weight = (weight > Database.PRICES.getWeekendPrice()) ? weight : Database.PRICES.getWeekendPrice();
        }

        return weight;
    }
}
