package model;

/**
 * Created by YMC on 24/05/2017.
 */

public class Folder {
    private int amount;
    String date ;
    String id;
    ThirdParty opponent;
    String state;
    String type;
    String picture;
    String video;


    public Folder() {
    }

    public Folder(int amount, String date, String id, ThirdParty opponent, String state, String type, String picture, String video) {
        this.amount = amount;
        this.date = date;
        this.id = id;
        this.opponent = opponent;
        this.state = state;
        this.type = type;
        this.picture = picture;
        this.video = video;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ThirdParty getOpponent() {
        return opponent;
    }

    public void setOpponent(ThirdParty opponent) {
        this.opponent = opponent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
