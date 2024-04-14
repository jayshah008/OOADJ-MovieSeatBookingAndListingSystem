package src.model;

import java.io.Serializable;

public class MovieTicket implements Serializable {

    private String UUID;

    private String seatID;

    private String movieUUID;

    private DateTime showTime;

    private String cinemaUUID;

    private final static long serialVersionUID = 14L;

    public MovieTicket(String UUID, String movieUUID, DateTime showTime, String seatID, String cinemaUUID) {
        this.setUUID(UUID);
        this.setSeatID(seatID);
        this.setMovieToWatch(movieUUID);
        this.setShowTime(showTime);
        this.setShowingVenue(cinemaUUID);
    }

    public String getUUID() {
        return this.UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getMovieToWatch() {
        return this.movieUUID;
    }

    public void setMovieToWatch(String movieToWatch) {
        this.movieUUID = movieToWatch;
    }

    public DateTime getShowTime() {
        return this.showTime;
    }

    public void setShowTime(DateTime showTime) {
        this.showTime = showTime;
    }

    public String getShowingVenue() {
        return this.cinemaUUID;
    }

    public void setShowingVenue(String showingVenue) {
        this.cinemaUUID = showingVenue;
    }

    public String getSeatID() {
        return seatID;
    }

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }
}
