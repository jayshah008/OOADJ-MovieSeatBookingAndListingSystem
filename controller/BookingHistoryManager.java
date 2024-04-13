package src.controller;

import java.util.*;
import src.database.*;
import src.handler.*;
import src.model.*;

/**
 * Controller for handling all logic related to {@link BookingHistory}
 * 
 * @author Lee Juin
 * @version 1.0
 */
public class BookingHistoryManager {

    /**
     * Constructor
     */
    public BookingHistoryManager() {
    }

    /**
     * Method to create a {@link BookingHistory} instance and save to database
     * 
     * @param movieTicket is all the {@link MovieTicket} bought in a single
     *                    {@link Payment}
     * @param payment     is the {@link Payment} made in relation to the tickets
     *                    bought
     * @param movieGoer   is the {@link MovieGoer} who bought the tickets
     * @return The created booking history
     */
    public static BookingHistory createBookingHistory(ArrayList<MovieTicket> movieTicket, Payment payment,
            MovieGoer movieGoer) {
        String UUID = String.format("BH%04d", DatabaseHandler.generateUUID(Database.BOOKING_HISTORY));
        BookingHistory bookingHistory = new BookingHistory(UUID, movieTicket, payment);
        movieGoer.addBookingHistory(bookingHistory);
        DatabaseManager.saveUpdateToDatabase(UUID, bookingHistory, Database.BOOKING_HISTORY);
        DatabaseManager.saveUpdateToDatabase(movieGoer.getUUID(), movieGoer, Database.MOVIE_GOER);
        return bookingHistory;
    }

    /**
     * Method to retrieve a booking history by its UUID
     * 
     * @param bookingHistoryUUID is the unique ID of the booking history
     * @return The {@link BookingHistory} retrieved
     */
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
