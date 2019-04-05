package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Integer Id;

    @Basic
    @JsonProperty("name")
    private String name;

    @Basic
    @JsonProperty("address")
    private String address;

    @Basic
    @JsonProperty("phno")
    private String phno;

    @Basic
    @JsonProperty("workinghrs")
    private String workinghrs;

    @Basic
    @JsonProperty("cost")
    private Integer cost;

    @Basic
    @JsonProperty("cuisines")
    private String[] cuisines;

    @Basic
    @JsonProperty("featured_in")
    private String[] featured_in;

    @Basic
    @JsonProperty("type")
    private String type;

    @Basic
    @JsonProperty("latitude")
    @Column(precision = 8, scale = 5)
    private Double latitude;

    @Basic
    @JsonProperty("longitude")
    @Column(precision = 8, scale = 5)
    private Double longitude;

    @Basic
    @JsonIgnore
    @JsonProperty("avgRating")
    private Double avgRating;

    @Transient
    @JsonIgnore
    @JsonProperty("menuUrls")
    private String[] menuUrls;

    @Transient
    @JsonIgnore
    @JsonProperty("imageUrls")
    private String[] imageUrls;



    public Restaurant() {
    }


    public Restaurant(Integer id, String name, String address, String phno, String workinghrs, Integer cost, String[] cuisines, String[] featured_in, String type, Double latitude, Double longitude, Double avgRating, String[] menuUrls, String[] imageUrls) {
        Id = id;
        this.name = name;
        this.address = address;
        this.phno = phno;
        this.workinghrs = workinghrs;
        this.cost = cost;
        this.cuisines = cuisines;
        this.featured_in = featured_in;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avgRating = avgRating;
        this.menuUrls = menuUrls;
        this.imageUrls = imageUrls;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getWorkinghrs() {
        return workinghrs;
    }

    public void setWorkinghrs(String workinghrs) {
        this.workinghrs = workinghrs;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String[] getCuisines() {
        return cuisines;
    }

    public void setCuisines(String[] cuisines) {
        this.cuisines = cuisines;
    }

    public String[] getFeatured_in() {
        return featured_in;
    }

    public void setFeatured_in(String[] featured_in) {
        this.featured_in = featured_in;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String[] getMenuUrls() {
        return menuUrls;
    }

    public void setMenuUrls(String[] menuUrls) {
        this.menuUrls = menuUrls;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }
}