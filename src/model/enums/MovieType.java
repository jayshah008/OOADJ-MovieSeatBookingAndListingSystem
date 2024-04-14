package src.model.enums;


public enum MovieType {

    Standard("Standard"),

    Blockbuster("Blockbuster"),

    ThreeD("3D");

    private final String display;

    private MovieType(String display) {
        this.display = display;
    }

    public String getDisplayName() {
        return this.display;
    }
}