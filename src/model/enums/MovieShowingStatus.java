package src.model.enums;

public enum MovieShowingStatus {

    COMING_SOON("Coming Soon"),

    PREVIEW("Preview"),

    NOW_SHOWING("Now Showing"),

    END_OF_SHOWING("End of Showing");

    private final String display;

    private MovieShowingStatus(String display) {
        this.display = display;
    }

    public String getDisplayName() {
        return this.display;
    }
}