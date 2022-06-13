package com.a1tech.wotwallpapers.Model;

public class Country {
    private String countryName;
    private String countryImage;
    private String country;

    public Country(String countryName, String countryImage, String country) {
        this.countryName = countryName;
        this.countryImage = countryImage;
        this.country = country;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryImage() {
        return countryImage;
    }

    public String getCountry() {
        return country;
    }
}
