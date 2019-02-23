package dao;

import models.Book;
import models.Restaurant;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;


public class RestaurantDaoImpl implements RestaurantDao{

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
    public Restaurant update(Restaurant restaurant) {
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
