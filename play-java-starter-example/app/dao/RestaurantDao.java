package dao;

import models.Restaurant;

import java.util.Collection;
import java.util.Optional;

public interface RestaurantDao extends CrudDAO<Restaurant, Integer>{

    Collection<Restaurant> findRestaurantByName(String name);
    Collection<Restaurant> findRestaurantByLocation(Double lat, Double lng);
    Double averageRating(Integer rid);
    Collection<Restaurant> findRestaurantByFilters(String type, Integer minCost, Integer maxCost, String cuisines, Integer Sort, String Open);


}