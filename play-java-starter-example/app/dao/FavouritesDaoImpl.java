package dao;

import controllers.RestaurantController;
import controllers.UserController;
import models.Favourites;
import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FavouritesDaoImpl implements FavouritesDao {

    private final static Logger.ALogger LOGGER = Logger.of(UserController.class);

    @Inject
    public FavouritesDaoImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    final JPAApi jpaApi;

    @Override
    public Integer[] FavouriteById(Integer id) {
        if(null == id) {
            throw new IllegalArgumentException("id must be provided");
        }

        try {
            String queryString = "SELECT favres FROM Favourites WHERE user_id= '" + id +"' ";
            LOGGER.debug("queryString {}", queryString);
            TypedQuery<Integer> query = jpaApi.em().createQuery(queryString, Integer.class);
            List<Integer> favs =  query.getResultList();

            LOGGER.debug("favs {}", favs);
            LOGGER.debug("size{}",favs.size());
            Integer[] arr = new Integer[favs.size()];
            for (int i =0; i < favs.size(); i++) {
                arr[i] = favs.get(i);
                LOGGER.debug("arr1 {}", arr[i]);
            }
            LOGGER.debug("arr {}", arr);
            return arr;
        }
        catch(NoResultException nre) {
            throw new IllegalArgumentException("No image found corresponding to given home id");
        }
    }

    @Override
    public Favourites create(Favourites entity) {
        jpaApi.em().persist(entity);
        return entity;
    }

    @Override
    public Optional<Favourites> read(Integer id) {
        return Optional.empty();
    }

    @Override
    public Favourites update(Favourites favourites) {
        return null;
    }

    @Override
    public Favourites delete(Integer id) {


        if(null == id) {
            throw new IllegalArgumentException("Id must be provided");
        }

        final Favourites favourites = jpaApi.em().find(Favourites.class, id);
        if(null == favourites) {
            return null;
        }

        jpaApi.em().remove(favourites);
        return favourites;
    }

    @Override
    public Collection<Favourites> all() {
        return null;
    }
}
