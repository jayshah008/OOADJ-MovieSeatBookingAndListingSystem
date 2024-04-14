package src.model.enums;

public enum SeatType {

    STANDARD("Standard Seat"),

    COUPLE("Couple Seat");

    private final String display;

    private SeatType(String display) {
        this.display = display;
    }

    public String getDisplayName() {
        return this.display;
    }
}
