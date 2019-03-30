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
    private String Name;

    @Basic
    @JsonProperty("address")
    private String Address;

    @Basic
    @JsonProperty("phno")
    private String PhNo;

    @Basic
    @JsonProperty("workinghrs")
    private String WorkingHrs;

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

    public Restaurant(Integer id, String name, String address, String phNo, String workingHrs, String[] menuUrls, String[] imageUrls) {
        Id = id;
        Name = name;
        Address = address;
        PhNo = phNo;
        WorkingHrs = workingHrs;
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
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhNo() {
        return PhNo;
    }

    public void setPhNo(String phNo) {
        PhNo = phNo;
    }

    public String getWorkingHrs() {
        return WorkingHrs;
    }

    public void setWorkingHrs(String workingHrs) {
        WorkingHrs = workingHrs;
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
}