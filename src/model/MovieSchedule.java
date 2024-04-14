package src.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieSchedule implements Serializable {

    private String UUID;

    private String movieUUID;

    private ArrayList<String> showingVenueUUID;

    private ArrayList<ArrayList<Seat>> seatingPlan;

    private ArrayList<DateTime> showingTime;
    private static final long serialVersionUID = 5L;

    public MovieSchedule(String UUID, String movieUUID, ArrayList<String> showingVenueUUID,
            ArrayList<ArrayList<Seat>> seatingPlan, ArrayList<DateTime> showingTime) {
        this.setUUID(UUID);
        this.setMovieOnShow(movieUUID);
        this.setShowingVenues(showingVenueUUID);
        this.setSeatingPlan(seatingPlan);
        this.setShowingTime(showingTime);
    }

    public String getUUID() {
        return this.UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getMovieOnShow() {
        return this.movieUUID;
    }

    public void setMovieOnShow(String movieUUID) {
        this.movieUUID = movieUUID;
    }

    public ArrayList<String> getShowingVenues() {
        return this.showingVenueUUID;
    }

    public void setShowingVenues(ArrayList<String> showingVenueUUID) {
        this.showingVenueUUID = showingVenueUUID;
    }

    public void addShowingVenue(String cinemaUUID) {
        this.showingVenueUUID.add(cinemaUUID);
    }

    public ArrayList<ArrayList<Seat>> getSeatingPlan() {
        return this.seatingPlan;
    }

    public void setSeatingPlan(ArrayList<ArrayList<Seat>> seatingPlan) {
        this.seatingPlan = seatingPlan;
    }

    public void addSeatingPlan(ArrayList<Seat> seatingPlan) {
        this.seatingPlan.add(seatingPlan);
    }

    public void removeSeatingPlan(int index) {
        this.getSeatingPlan().remove(index);
    }

    public void setSeatStatus(int venueSlot, Seat seat, boolean assign) {
        ArrayList<Seat> seats = this.seatingPlan.get(venueSlot);

        int index = seats.indexOf(seat);
        this.seatingPlan.get(venueSlot).get(index).setAssignStatus(assign);
    }

    public ArrayList<DateTime> getShowingTime() {
        return this.showingTime;
    }

    public void setShowingTime(ArrayList<DateTime> showingTime) {
        this.showingTime = showingTime;
    }

    public void addShowingTime(DateTime showingTime) {
        this.showingTime.add(showingTime);
    }

    public void removeShowingVenue(int index) {
        this.getShowingVenues().remove(index);
    }

    public void removeShowingTime(int index) {
        this.getShowingTime().remove(index);
    }
}
