package dao;

import controllers.RestaurantController;
import models.Images;
import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ImageDaoImpl implements ImageDao{

    private final static Logger.ALogger LOGGER = Logger.of(RestaurantController.class);

    final JPAApi jpaApi;

    @Inject
    public ImageDaoImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public String[] getImageById(Integer id){

//        if(null == id){
//            throw new IllegalArgumentException("id must be provided");
//        }
//
//        LOGGER.debug(String.valueOf(id));
//        Query query= jpaApi.em().createQuery("SELECT imageUrl FROM Images where id = '" + id + "'", Images.class);
//        List images = query.getResultList();
//
//        return images;

        LOGGER.debug("id is " + id);


        if(null == id) {
            throw new IllegalArgumentException("id must be provided");
        }

        try {
            String queryString = "SELECT imageUrl FROM Images WHERE restaurant.id = '" + id +"' ";
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
    public Images create(Images entity) {
        jpaApi.em().persist(entity);
        return entity;
    }

    @Override
    public Optional<Images> read(String id) {
        return Optional.empty();
    }

    @Override
    public Images update(Images entity) {
        return null;
    }



    @Override
    public Images delete(String id) {
        return null;
    }

    @Override
    public Collection<Images> all() {
        return null;
    }

}
