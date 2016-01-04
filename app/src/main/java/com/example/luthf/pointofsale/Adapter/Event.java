package com.example.luthf.pointofsale.Adapter;

/**
 * Created by luthf on 9/29/2015.
 */
public class Event {
    private String name, thumbnailUrl, sum, price, letter;

    public Event() {
    }

    public Event(String name, String thumbnailUrl, String year, String rating) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.sum = sum;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

}
