package src.database;

/**
 * Enumeration class for handling conversion from database file to HashMap
 * 
 * @author Lee Juin
 * @version 1.0
 */
public enum ModelType {

    /**
     * Cineplex enum and its file name
     */
    CINEPLEX("Cineplex"),

    /**
     * Cinema enum and its file name
     */
    CINEMA("Cinema"),

    /**
     * Seat enum and its file name
     */
    SEAT("Seat"),

    /**
     * MovieSchedule enum and its file name
     */
    MOVIE_SCHEDULE("MovieSchedule"),

    /**
     * BookingHistory enum and its file name
     */
    BOOKING_HISTORY("BookingHistory"),

    /**
     * MovieGoer enum and its file name
     */
    MOVIE_GOER("MovieGoer"),

    /**
     * Movie enum and its file name
     */
    MOVIE("Movie"),

    /**
     * MovieReview enum and its file name
     */
    MOVIE_REVIEW("MovieReview"),

    /**
     * CinemaStaff enum and its file name
     */
    CINEMA_STAFF("CinemaStaff"),

    /**
     * Holiday enum and its file name
     */
    HOLIDAY("Holiday"),

    /**
     * Prices enum and its file name
     */
    PRICES("Prices"),

    /**
     * MovieTicket enum and its file name
     */
    MOVIE_TICKET("MovieTicket"),

    /**
     * Payment enum and its file name
     */
    PAYMENT("Payment"),

    /**
     * Permission enum and its file name
     */
    PERMISSION("Permission");

    /**
     * File name of the model
     */
    private final String fileName;

    /**
     * Private constructor
     * 
     * @param fileName is the file name to be used for the model
     */
    private ModelType(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the file name of the model
     * 
     * @return The file name of the model
     */
    public String getFileName() {
        return this.fileName;
    }
}
