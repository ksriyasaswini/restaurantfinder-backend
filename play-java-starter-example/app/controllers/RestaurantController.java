package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.ImageDao;
import dao.RestaurantDao;
import dao.RestaurantDaoImpl;
import models.Images;
import models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

public class RestaurantController extends Controller {

    private final static Logger.ALogger LOGGER = Logger.of(RestaurantController .class);

    @Autowired
    final RestaurantDao restaurantDao;
    private ImageDao imageDao;

    @Inject
    public RestaurantController(RestaurantDao restaurantDao, ImageDao imageDao) {
        this.restaurantDao = restaurantDao;
        this.imageDao = imageDao;
    }

    @Transactional
    public Result createRestaurant() {

        final JsonNode json = request().body().asJson();

        final Restaurant restaurant = Json.fromJson(json, Restaurant.class);

        LOGGER.debug("Restaurant name = " + restaurant.getName());
        LOGGER.error("This is an error");

        if (null == restaurant.getName()) {
            return badRequest("Name must be provided");
        }

        final Restaurant newrestaurant = restaurantDao.create(restaurant);

        for (String url : restaurant.getImageUrls()) {
            final Images image = new Images(url);
            image.setImageUrl(url);
            image.setRestaurant(newrestaurant);
            imageDao.create(image);
        }


        final JsonNode result = Json.toJson(newrestaurant);
        return ok(result);
    }

    @Transactional
    public Result getRestaurantByName(String name) {

        if (null == name) {
            return badRequest("name must be provided");
        }

        final Collection<Restaurant> restaurants = restaurantDao.findRestaurantByName(name);
//        if(restaurants.isPresent()) {
//            final JsonNode result = Json.toJson(restaurants.get());
//            return ok(result);
//        } else {
//            return notFound();
//        }

        for(Restaurant restaurant_new: restaurants ){
            String[] image_strings = imageDao.getImageById(restaurant_new.getId());
            LOGGER.debug("img collection is "+ image_strings);
            restaurant_new.setImageUrls(image_strings);
        }
        final JsonNode result = Json.toJson(restaurants);

        return ok(result);
    }

    @Transactional
    public Result getRestaurantById(Integer id) {

        if (null == id) {
            return badRequest("name must be provided");
        }

        final Collection<Restaurant> restaurants = restaurantDao.findRestaurantById(id);
        for(Restaurant restaurant_new: restaurants ){
            String[] image_strings = imageDao.getImageById(restaurant_new.getId());
            LOGGER.debug("img collection is "+ image_strings);
            restaurant_new.setImageUrls(image_strings);
        }

        final JsonNode result = Json.toJson(restaurants);

        return ok(result);
    }


    @Transactional
    public Result getAllRestaurants() {

        Collection<Restaurant> restaurants = restaurantDao.all();

        for(Restaurant restaurant_new: restaurants ){
            String[] image_strings = imageDao.getImageById(restaurant_new.getId());
            LOGGER.debug("img collection is "+ image_strings);
            restaurant_new.setImageUrls(image_strings);
        }

        final JsonNode result = Json.toJson(restaurants);

       return ok(result);
    }

}
