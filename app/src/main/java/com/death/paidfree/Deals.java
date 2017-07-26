package com.death.paidfree;


import io.realm.RealmObject;

/**
 * Created by deathcode on 20/07/17.
 */

public class Deals extends RealmObject {
    private String id;

    private long add_time;

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    private String add_title;

    private String description;


    private String category;


    private String link;


    private String poster;


    private String price;


    private String new_price;

    public  Deals()
    {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAdd_title(String add_title) {
        this.add_title = add_title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setNew_price(String new_price) {
        this.new_price = new_price;
    }

    public String getId() {
        return id;
    }

    public String getAdd_title() {
        return add_title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getLink() {
        return link;
    }

    public String getPoster() {
        return poster;
    }

    public String getPrice() {
        return price;
    }

    public String getNew_price() {
        return new_price;
    }
}