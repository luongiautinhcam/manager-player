package vn.edu.stu.quanlycauthu.model;

import java.io.Serializable;
import java.util.Date;

public class Player implements Serializable {
    Integer id;
    String name;
    Date dob;
    String postion;
    byte[] image;
    String idClub;
    String nameClub;

    public Player() {
    }

    public Player(Integer id, String name, Date dob, String postion, byte[] image, String idClub, String nameClub) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.postion = postion;
        this.image = image;
        this.idClub = idClub;
        this.nameClub = nameClub;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getIdClub() {
        return idClub;
    }

    public void setIdClub(String idClub) {
        this.idClub = idClub;
    }

    public String getNameClub() {
        return nameClub;
    }

    public void setNameClub(String nameClub) {
        this.nameClub = nameClub;
    }
}
