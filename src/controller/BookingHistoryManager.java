package src.controller;

import java.util.*;
import src.database.*;
import src.handler.*;
import src.model.*;


public class BookingHistoryManager {

    public BookingHistoryManager() {
    }

   
    public static BookingHistory createBookingHistory(ArrayList<MovieTicket> movieTicket, Payment payment,
            MovieGoer movieGoer) {
        String UUID = String.format("BH%04d", DatabaseHandler.generateUUID(Database.BOOKING_HISTORY));
        BookingHistory bookingHistory = new BookingHistory(UUID, movieTicket, payment);
        movieGoer.addBookingHistory(bookingHistory);
        DatabaseManager.saveUpdateToDatabase(UUID, bookingHistory, Database.BOOKING_HISTORY);
        DatabaseManager.saveUpdateToDatabase(movieGoer.getUUID(), movieGoer, Database.MOVIE_GOER);
        return bookingHistory;
    }

    public static BookingHistory getBookingHistoryByUUID(String bookingHistoryUUID) {
        ArrayList<BookingHistory> bookingHistories = Database.getValueList(Database.BOOKING_HISTORY.values());

        for (int i = 0; i < bookingHistories.size(); i++) {
            BookingHistory bookingHistory = bookingHistories.get(i);
            if (bookingHistory.getUUID().equals(bookingHistoryUUID)) {
                return bookingHistory;
            }
        }
        return null;
    }
}
