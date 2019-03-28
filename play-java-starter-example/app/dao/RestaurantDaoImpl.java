package dao;

import controllers.RestaurantController;
import models.Book;
import models.Restaurant;
import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class RestaurantDaoImpl implements RestaurantDao{

    private final static Logger.ALogger LOGGER = Logger.of(RestaurantController.class);
    final JPAApi jpaApi;

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
        TypedQuery<Restaurant> query = jpaApi.em().createQuery("SELECT r FROM Restaurant r where Address LIKE '%"+name+"%'", Restaurant.class);
        List<Restaurant> restaurants= query.getResultList();

        return restaurants;
    }

}
