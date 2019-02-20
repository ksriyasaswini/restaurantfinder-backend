package dao;

import models.Restaurant;

import java.util.Collection;

public interface RestaurantDao extends Crud2DAO<Restaurant, Integer>{

    Collection<Restaurant> findRestaurantByName(String name);

}