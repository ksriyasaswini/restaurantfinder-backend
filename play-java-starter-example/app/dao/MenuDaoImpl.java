package dao;

import controllers.RestaurantController;
import models.Images;
import models.Menu;
import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MenuDaoImpl implements MenuDao{

    private final static Logger.ALogger LOGGER = Logger.of(RestaurantController.class);

    final JPAApi jpaApi;

    @Inject
    public MenuDaoImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public String[] getMenuById(Integer id){

        LOGGER.debug("id is " + id);


        if(null == id) {
            throw new IllegalArgumentException("id must be provided");
        }

        try {
            String queryString = "SELECT imageUrl FROM Menu WHERE restaurant.id = '" + id +"' ";
            LOGGER.debug("queryString {}", queryString);
            TypedQuery<String> query = jpaApi.em().createQuery(queryString, String.class);
            List<String> imageUrls =  query.getResultList();

            LOGGER.debug("ImageURLs {}", imageUrls);

            return imageUrls.toArray(new String[0]);
        }
        catch(NoResultException nre) {
            throw new IllegalArgumentException("No image found corresponding to given home id");
        }

    }

    @Override
    public Menu create(Menu entity) {
        jpaApi.em().persist(entity);
        return entity;
    }



    @Override
    public Optional<Menu> read(String id) {
        return Optional.empty();
    }

    @Override
    public Menu update(Menu menu) {
        return null;
    }

    @Override
    public Menu delete(String id) {
        return null;
    }

    @Override
    public Collection<Menu> all() {
        return null;
    }


}
