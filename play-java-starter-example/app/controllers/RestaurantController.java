package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.RestaurantDao;
import dao.RestaurantDaoImpl;
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

    @Inject
    public RestaurantController(RestaurantDao restaurantDao) { this.restaurantDao = restaurantDao; }
    //public UserController(UserDao userDao) { this.userDao = userDao; }

    @Transactional
    public Result createRestaurant() {

        final JsonNode json = request().body().asJson();

        final Restaurant restaurant = Json.fromJson(json, Restaurant.class);

        LOGGER.debug("Restaurant name = " + restaurant.getName());
        LOGGER.error("This is an error");

        if (null == restaurant.getName()) {
            return badRequest("Name must be provided");
        }


        restaurantDao.create(restaurant);
        final JsonNode result = Json.toJson(restaurant);
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
        final JsonNode result = Json.toJson(restaurants);

        return ok(result);
    }


    @Transactional
    public Result getAllRestaurants() {

        Collection<Restaurant> restaurant = restaurantDao.all();

        final JsonNode result = Json.toJson(restaurant);

        return ok(result);
    }

}
