package dao;

import models.UserDetails;

public interface UserDao extends Crud2DAO<UserDetails,Integer>{

    UserDetails findUserByName(String username);
    UserDetails findUserByAuthToken(String Token);

}
