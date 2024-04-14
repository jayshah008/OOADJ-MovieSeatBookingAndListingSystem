package src.model;

import java.io.Serializable;

public class Permission implements Serializable {
    private boolean movieGoerViewTop5ByOverallRatings;

    private boolean movieGoerViewTop5BySales;
    private final static long serialVersionUID = 17L;

    public Permission() {
        this.movieGoerViewTop5ByOverallRatings = true;
        this.movieGoerViewTop5BySales = true;
    }

    public boolean getMovieSalesPermission() {
        return this.movieGoerViewTop5BySales;
    }

    public void setMovieSalesPermission(boolean permission) {
        this.movieGoerViewTop5BySales = permission;
    }

    public boolean getOverallRatingsPermission() {
        return this.movieGoerViewTop5ByOverallRatings;
    }

    public void setOverallRatingsPermission(boolean permission) {
        this.movieGoerViewTop5ByOverallRatings = permission;
    }
}
