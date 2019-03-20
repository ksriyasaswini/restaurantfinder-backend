package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;


@Entity
public class Review {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @JsonProperty("revId")
        private Integer revId;

        @Basic
        @JsonProperty("rating")
        private Integer rating;

        @Basic
        @JsonProperty("review")
        private String review;

//        @Basic
//        @JoinColumn(name="uid")
//        private Integer uid;

        @ManyToOne
        private UserDetails user;

        @ManyToOne
        private Restaurant restaurant;

//        @Basic
//        @JsonProperty("rid")
//        private Integer rid;


//    public Review(Integer revId, Integer rating, String review,Integer rid, Integer uid){
//            this.revId = revId;
//            this.rating=rating;
//            this.review=review;
//            this.rid=rid;
//            this.uid=uid;
//        }


    public Review(Integer revId, Integer rating, String review, UserDetails user, Restaurant restaurant) {
        this.revId = revId;
        this.rating = rating;
        this.review = review;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Review() {
        }

    public Integer getRevId() {
        return revId;
    }

    public void setRevId(Integer revId) {
        this.revId = revId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

//    public Integer getRid() {
//        return rid;
//    }
//
//    public void setRid(Integer rid) {
//        this.rid = rid;
//    }
//
//    public Integer getUid() {
//        return uid;
//    }
//
//    public void setUid(Integer uid) {
//        this.uid = uid;
//    }


    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }


    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
