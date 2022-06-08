package com.a1tech.wotwallpapers.Model;

public class Tanks {

    private String name;
    private String img;
    private String imgPhone;
    private String country;
    private String id;

    public Tanks(String name, String img, String imgPhone, String country, String id) {
        this.name = name;
        this.img = img;
        this.imgPhone = imgPhone;
        this.country = country;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getImgPhone() {
        return imgPhone;
    }

    public String getCountry() {
        return country;
    }

    public String getId() {
        return id;
    }
}