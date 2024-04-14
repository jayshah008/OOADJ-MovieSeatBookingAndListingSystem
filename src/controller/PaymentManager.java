package src.controller;

import src.database.*;
import src.handler.*;
import src.model.*;
import src.model.enums.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;



public class PaymentManager {

    public PaymentManager() {
    }


    public static Payment createQRCodePayment(String transactionID, double movieTicketPrice) {
        String UUID = String.format("PY%04d", DatabaseHandler.generateUUID(Database.PAYMENT));
        Payment payment = new QRCodePayment(UUID, PaymentType.QRCODE, transactionID, movieTicketPrice);
        DatabaseManager.saveUpdateToDatabase(UUID, payment, Database.PAYMENT);
        return payment;
    }

    public static Payment createBankTransactioPayment(String transactionID, double movieTicketPrice) {
        String UUID = String.format("PY%04d", DatabaseHandler.generateUUID(Database.PAYMENT));
        Payment payment = new BankTransactionPayment(UUID, PaymentType.BANK_TRANSACTION, transactionID,
                movieTicketPrice);
        DatabaseManager.saveUpdateToDatabase(UUID, payment, Database.PAYMENT);
        return payment;
    }

    public static Payment createCardPayment(String transactionID, double movieTicketPrice) {
        String UUID = String.format("PY%04d", DatabaseHandler.generateUUID(Database.PAYMENT));
        Payment payment = new CardPayment(UUID, PaymentType.CARD_PAYMENT, transactionID, movieTicketPrice);
        DatabaseManager.saveUpdateToDatabase(UUID, payment, Database.PAYMENT);
        return payment;
    }


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


    public static String generateTransactionId(String cinemaCode) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime now = LocalDateTime.now();
        String transactionId = cinemaCode + dtf.format(now);
        return transactionId;
    }

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
