package esprit.tn.tunicamp.entities;

import java.security.Timestamp;

public class Activity {
    private int id;
    private int creator;
    private String name;
    private String dateTime;
    private String description;
    private int maxPeople;
    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Activity(int id, int creator, String name, String dateTime, String description, int maxPeople) {
        this.id = id;
        this.creator = creator;
        this.name = name;
        this.dateTime = dateTime;
        this.description = description;
        this.maxPeople = maxPeople;
    }
}