package dao;

import models.Restaurant;

import java.util.Collection;

public interface RestaurantDao extends CrudDAO<Restaurant, Integer>{

    Collection<Restaurant> findRestaurantByName(String name);
    Collection<Restaurant> findRestaurantById(Integer id);

}