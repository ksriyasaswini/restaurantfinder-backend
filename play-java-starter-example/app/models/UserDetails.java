package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Integer id;

    @Basic
    @JsonProperty("username")
    private String username;

    @Transient
    @JsonProperty("password")
    private String password;

    @Basic
    @JsonIgnore
    private String passwordHash;

    @Basic
    @JsonIgnore
    private String salt;

    @Basic
    @JsonIgnore
    private Integer hashIterations;

    @Basic
    private String accessToken;

    public UserDetails(Integer id, String username, String password, String passwordHash, String salt, Integer hashIteration) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.hashIterations = hashIterations;
    }

    public UserDetails() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(Integer hashIterations) {
        this.hashIterations = hashIterations;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}