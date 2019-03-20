package dao;

import models.UserDetails;

public interface UserDao extends CrudDAO<UserDetails,Integer>{

    UserDetails findUserByName(String username);
    UserDetails findUserByAuthToken(String Token);

}
