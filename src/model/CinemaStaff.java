package src.model;

import java.io.Serializable;

public class CinemaStaff implements Serializable {

    private String UUID;

    private String name;

    private String password;

    private String username;
    private static final long serialVersionUID = 11L;

    public CinemaStaff(String UUID, String name, String password, String username) {
        this.UUID = UUID;
        this.name = name;
        this.password = password;
        this.username = username;
    }

    public String getUUID() {
        return this.UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
