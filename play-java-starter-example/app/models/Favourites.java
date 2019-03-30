package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.File;
import java.nio.file.Path;

@Entity
public class Favourites {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("fid")
    private Integer fid;

    @Basic
    @JsonProperty("favres")
    private Integer favres;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private UserDetails user;

    public Favourites(Integer favres) {
        this.favres = favres;
    }

    public Favourites() {
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }

    public Integer getFavres() {
        return favres;
    }

    public void setFavres(Integer favres) {
        this.favres = favres;
    }
}
