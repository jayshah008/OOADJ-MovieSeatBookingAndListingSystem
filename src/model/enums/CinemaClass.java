package src.model.enums;

public enum CinemaClass {

    
    STANDARD("Standard Cinema"),

    PLATINUM("Platinum Movie Suite"),

    IMAX("IMAX Cinema Experience");

    private final String display;

    private CinemaClass(String display) {
        this.display = display;
    }

    public String getDisplayName() {
        return this.display;
    }
}
