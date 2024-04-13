package src.controller;

import java.util.ArrayList;

import src.database.*;
import src.handler.*;
import src.model.*;
import src.model.enums.*;

/**
 * Controller class for handling all logic related to {@link Seat}
 * 
 * @author Lee Juin
 * @version 1.0
 */
public class SeatManager {

    /**
     * Constructor
     */
    public SeatManager() {
    }

    /**
     * Method to create a {@link CoupleSeat} and save to database
     * 
     * @return The created {@link CoupleSeat} instace.
     */
    public static Seat createCoupleSeat() {
        String UUID = String.format("ST%04d", DatabaseHandler.generateUUID(Database.SEAT));
        Seat seat = new CoupleSeat(UUID);
        DatabaseManager.saveUpdateToBuffer(UUID, seat, Database.SEAT);
        return seat;
    }

    /**
     * Method to create a {@link StandardSeat} and save to database
     * 
     * @return The created {@link StandardSeat} instace.
     */
    public static Seat createStandardSeat() {
        String UUID = String.format("ST%04d", DatabaseHandler.generateUUID(Database.SEAT));
        Seat seat = new StandardSeat(UUID);
        DatabaseManager.saveUpdateToBuffer(UUID, seat, Database.SEAT);
        return seat;
    }

    /**
     * Method to print the floor map of a standard cinema (non-platinum)
     * 
     * @param seatingPlan is the seating plan to be printed
     */
    public static void printStandardCinemaFloorMap(ArrayList<Seat> seatingPlan) {
        int totalNumOfSeatsPerRow = Database.totalNumOfSeats / Database.numOfRows;
        int index = 0;
        System.out.print("    ");
        for (int i = 0; i < totalNumOfSeatsPerRow; i++) {
            if (i == totalNumOfSeatsPerRow / 2) {
                System.out.print("      ");
            }
            System.out.print("[ " + String.format("%02d", i + 1) + "]");
        }

        System.out.println("");
        System.out.println("");

        Character row = 'A';
        for (int i = 0; i < Database.numOfCoupleRows; i++) {
            System.out.print(row);
            System.out.print("   ");
            row++;
            for (int j = 0; j < totalNumOfSeatsPerRow; j += 2) {
                Seat seat = seatingPlan.get(index);
                if (j == totalNumOfSeatsPerRow / 2) {
                    System.out.print("      ");
                }

                if (seat.getAssignStatus()) {
                    System.out.print("[   XX   ]");
                } else {
                    System.out.print("[        ]");
                }
                index += 2;
            }
            System.out.println("");
        }

        for (int i = 0; i < Database.numOfRows - Database.numOfCoupleRows; i++) {
            System.out.print(row);
            System.out.print("   ");
            row++;
            for (int j = 0; j < totalNumOfSeatsPerRow; j++) {
                Seat seat = seatingPlan.get(index);
                if (j == totalNumOfSeatsPerRow / 2) {
                    System.out.print("      ");
                }

                if (seat.getAssignStatus()) {
                    System.out.print("[ X ]");
                } else {
                    System.out.print("[   ]");
                }
                index++;
            }
            System.out.println("");
        }

        System.out.println("");
        System.out.println("""
                                                  Screen
                """);
    }

    /**
     * Method to print the floor map of a platinum cinema
     * 
     * @param seatingPlan is the seating plan to be printed
     */
    public static void printPlatinumCinemaFloorMap(ArrayList<Seat> seatingPlan) {
        int totalNumOfSeatsPerRow = Database.platinumNumOfSeatsPerRow;
        int numOfRows = Database.platinumNumOfRow;

        int index = 0;
        System.out.print("    ");
        for (int i = 0; i < totalNumOfSeatsPerRow; i++) {
            if (i == totalNumOfSeatsPerRow / 2) {
                System.out.print("      ");
            }
            System.out.print("[  " + String.format("%02d", i + 1) + " ]");
        }

        System.out.println("");
        System.out.println("");

        Character row = 'A';
        for (int i = 0; i < numOfRows; i++) {
            System.out.print(row);
            System.out.print("   ");
            row++;
            for (int j = 0; j < totalNumOfSeatsPerRow; j++) {
                Seat seat = seatingPlan.get(index);
                if (j == totalNumOfSeatsPerRow / 2) {
                    System.out.print("      ");
                }

                if (seat.getAssignStatus()) {
                    System.out.print("[  X  ]");
                } else {
                    System.out.print("[     ]");
                }
                index++;
            }
            System.out.println("");
            System.out.println("");
        }

        System.out.println("");
        System.out.println("""
                                 Screen
                """);
    }

    /**
     * Method to convert the front-end displayed seat ID (i.e. A1, B3, etc.) into
     * back-end seatID for processing
     * 
     * @param seatID is the front-end displayed seat ID
     * @param cinema is the cinema chosen by the MovieGoer
     * @return The back-end convereted seatID
     */
    public static int seatIDConverter(String seatID, Cinema cinema) {
        int totalNumOfSeatsPerRow;
        if (cinema.getCinemaClass() == CinemaClass.PLATINUM) {
            totalNumOfSeatsPerRow = Database.platinumNumOfSeatsPerRow;
        } else {
            totalNumOfSeatsPerRow = Database.totalNumOfSeats / Database.numOfRows;
        }
        char row = seatID.charAt(0);
        int rowConverted = row - 65;
        int colConverted = Integer.parseInt(seatID.substring(1));

        if (colConverted > totalNumOfSeatsPerRow) {
            return -1;
        }
        int convertedSeatID = rowConverted * (totalNumOfSeatsPerRow) + colConverted - 1;
        return convertedSeatID;
    }

    /**
     * Helper function to verify that the act of booking/unbooking is legitimate
     * 
     * @param seat    is the {@link Seat} instance to be booked/unbooked
     * @param booking is the booking status {@code true} to represent book and
     *                {@code false} to represent unbook
     * @return {@code true} if the act is legitimate, {@code false} otherwise
     */
    public static boolean validateBooking(Seat seat, boolean booking) {
        if (seat.getAssignStatus() == booking) {
            return false;
        }
        return true;
    }

    /**
     * Method to book/unbook seat
     * 
     * @param seatID        is the front-end displayed seat ID
     * @param movieSchedule is the {@link MovieSchedule} instance of the movie
     * @param seatingPlan   is the seating plan selected by the user
     * @param cinema        is the {@link Cinema} instance the MovieGoer chosen
     * @param assign        {@code true} if booking, otherwise {@code false}
     * @return {@code true} if booking/unbooking is successful, {@code false}
     *         otherwise
     */
    public static boolean bookSeat(String seatID, MovieSchedule movieSchedule, ArrayList<Seat> seatingPlan,
            Cinema cinema, boolean assign) {
        int index = SeatManager.seatIDConverter(seatID, cinema);
        Seat seatToBook = seatingPlan.get(index);

        if (seatToBook.getSeatType() == SeatType.STANDARD) {
            if (validateBooking(seatToBook, assign)) {
                seatToBook.setAssignStatus(assign);
                return true;
            } else {
                return false;
            }
        } else {
            Seat nextSeat;
            int nextSeatIndex;

            if (index % 2 == 0) {
                nextSeatIndex = index + 1;
            } else {
                nextSeatIndex = index - 1;
            }
            nextSeat = seatingPlan.get(nextSeatIndex);

            if (validateBooking(seatToBook, assign) && validateBooking(nextSeat, assign)) {
                seatToBook.setAssignStatus(assign);
                nextSeat.setAssignStatus(assign);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Method to obtain {@link Seat} instance by the front-end displayed seat ID
     * 
     * @param seatID      is the front-end displayed seat ID
     * @param seatingPlan is the seating plan that the Movie Goer chosen
     * @param cinema      is the {@link Cinema} that the Movie Goer chosen
     * @return The {@link Seat} instance
     */
    public static Seat getSeatBySeatID(String seatID, ArrayList<Seat> seatingPlan, Cinema cinema) {
        int index = SeatManager.seatIDConverter(seatID, cinema);
        return seatingPlan.get(index);
    }

    /**
     * Method to retrieve {@link Seat} instance by its UUID
     * 
     * @param seatUUID The target seat's UUID
     * @return The target seat
     */
    public static Seat getSeatByUUID(String seatUUID) {
        ArrayList<Seat> seats = Database.getValueList(Database.SEAT.values());

        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            if (seat.getUUID().equals(seatUUID)) {
                return seat;
            }
        }
        return null;
    }

    /**
     * Method to get the next seat's ID for couple seat
     * @param seatID is the current seat's ID
     * @param cinema is the target cinema
     * @return The next seat's ID
     */
    public static String getNextSeatID(String seatID, Cinema cinema) {
        String ind = seatID.substring(1);
        int index = Integer.parseInt(ind);

        if (index % 2 == 0) {
            index = index - 1;
        } else {
            index = index + 1;
        }

        String id = String.format("%s", seatID.substring(0, 1));
        id += String.format("%d", index);
        System.out.println(id);
        return id;
    }
}
