package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.File;
import java.nio.file.Path;

@Entity
public class Menu {


    @Id
    @JsonProperty("imageUrl")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="id")
    private Restaurant restaurant;

    public Menu(String imageUrl){

        this.imageUrl=imageUrl;
    }

    public Menu() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
