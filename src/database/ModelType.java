package src.database;

public enum ModelType {

    CINEPLEX("Cineplex"),

    CINEMA("Cinema"),

    SEAT("Seat"),

    MOVIE_SCHEDULE("MovieSchedule"),

    BOOKING_HISTORY("BookingHistory"),

    MOVIE_GOER("MovieGoer"),

    MOVIE("Movie"),

    MOVIE_REVIEW("MovieReview"),

    CINEMA_STAFF("CinemaStaff"),

    HOLIDAY("Holiday"),

    PRICES("Prices"),

    MOVIE_TICKET("MovieTicket"),

    PAYMENT("Payment"),

    PERMISSION("Permission");

    private final String fileName;

    private ModelType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}
