package dao;

import controllers.RestaurantController;
import models.Book;
import models.Restaurant;
import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class RestaurantDaoImpl implements RestaurantDao{


    private final static Logger.ALogger LOGGER = Logger.of(RestaurantController.class);
    final JPAApi jpaApi;
    List<Restaurant> restaurants;
    @Inject
    public RestaurantDaoImpl(JPAApi jpaApi) {

        this.jpaApi = jpaApi;

    }


    public Restaurant create(Restaurant restaurant) {

        if(null == restaurant) {
            throw new IllegalArgumentException("Book must be provided");
        }

        jpaApi.em().persist(restaurant);

        return restaurant;

    }

    @Override
    public Optional<Restaurant> read(Integer id) {
        if(null == id) {
            throw new IllegalArgumentException("Id must be provided");
        }

        final Restaurant restaurant = jpaApi.em().find(Restaurant.class, id);
        return restaurant != null ? Optional.of(restaurant) : Optional.empty();
    }

    @Override
    public Restaurant update(Restaurant restaurant) {

        if (null == restaurant) {
            throw new IllegalArgumentException("Book must be provided");
        }

        if (null == restaurant.getId()) {
            throw new IllegalArgumentException("Book id must be provided");
        }

        final Restaurant existingHotel = jpaApi.em().find(Restaurant.class, restaurant.getId());
        if (null == existingHotel) {
            return null;
        }

        existingHotel.setName(restaurant.getName());

        jpaApi.em().persist(existingHotel);

        return existingHotel;
    }

    @Override
    public Restaurant delete(Integer id) {
        return null;
    }

    @Override
    public Collection<Restaurant> all() {
        TypedQuery<Restaurant> query = jpaApi.em().createQuery("SELECT r FROM Restaurant r", Restaurant.class);
        List<Restaurant> restaurants= query.getResultList();

        return restaurants;
    }


    @Override
    public Collection<Restaurant> findRestaurantByName(String name) {
        TypedQuery<Restaurant> query = jpaApi.em().createQuery("SELECT r FROM Restaurant r where Address LIKE '%"+name+"%' or cuisines LIKE '%"+name+"%' or name LIKE '%"+name+"%' or featured_in LIKE '%"+name+"%'", Restaurant.class);
        List<Restaurant> restaurants= query.getResultList();

        return restaurants;
    }

    @Override
    public Collection<Restaurant> findRestaurantByLocation(Double lat, Double lng) {

        final String queryString = "SELECT r from Restaurant r where (6371 * acos(cos( radians("+lat+") ) * cos( radians( r.latitude ) ) * cos( radians( r.longitude ) - radians("+lng+") ) + sin( radians("+lat+") ) * sin( radians( r.latitude ) ) ) ) < 25";

        LOGGER.error("query {}", queryString);

        TypedQuery<Restaurant> query = jpaApi.em().createQuery(queryString, Restaurant.class);

        List<Restaurant> restaurants= query.getResultList();


        return restaurants;

    }


    @Override
    public Double averageRating(Integer rid) {

        TypedQuery<Double> query = jpaApi.em().createQuery("SELECT ROUND(AVG(rating),1) FROM Review r where restaurant_Id LIKE '%"+rid+"%' ", Double.class);
//TypedQuery<Integer> query = jpaApi.em().createQuery("SELECT AVG(rating) FROM Review r where restaurant_Id LIKE '%"+rid+"%' ", Review.class);

        Double ratings= query.getSingleResult();



        return ratings;
    }


    @Override
    public Collection<Restaurant> findRestaurantByFilters(String type, Integer minCost, Integer maxCost, String cuisines, Integer Sort,String Open) {


        if(Sort==1) {

            TypedQuery<Restaurant> query = jpaApi.em().createQuery("SELECT r FROM Restaurant r where type = '" + type + "' and cost <" + maxCost + " and cuisines LIKE '%" + cuisines + "%' ORDER BY avgRating desc", Restaurant.class);

            restaurants = query.getResultList();

        }

        else if(Sort ==2 ) {
            TypedQuery<Restaurant> query = jpaApi.em().createQuery("SELECT r FROM Restaurant r where type = '" + type + "' and cost between " + minCost + "and " + maxCost + " and cuisines LIKE '%" + cuisines + "%' ORDER BY cost desc", Restaurant.class);
            restaurants = query.getResultList();
        }

        else if(Sort ==3 ) {
            TypedQuery<Restaurant> query = jpaApi.em().createQuery("SELECT r FROM Restaurant r where type = '" + type + "' and cost between " + minCost + "and " + maxCost + " and cuisines LIKE '%" + cuisines + "%' ORDER BY cost asc", Restaurant.class);
            restaurants = query.getResultList();
        }
        else {
            TypedQuery<Restaurant> query = jpaApi.em().createQuery("SELECT r FROM Restaurant r where type = '" + type + "' and cost between " + minCost + "and " + maxCost + " and cuisines LIKE '%" + cuisines + "%'", Restaurant.class);
            restaurants = query.getResultList();
        }



        return restaurants;

    }
}
