package dao;

import models.UserDetails;

import java.util.Optional;

public interface UserDao extends CrudDAO<UserDetails,Integer>{

    UserDetails findUserByName(String username);
    UserDetails findUserByAuthToken(String Token);
    Optional<UserDetails> readbyname(UserDetails userDetails);

}
