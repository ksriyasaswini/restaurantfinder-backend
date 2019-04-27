package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.ImageDao;
import dao.MenuDao;
import dao.RestaurantDao;
import models.Images;
import models.Menu;
import models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class RestaurantController extends Controller {

    private final static Logger.ALogger LOGGER = Logger.of(RestaurantController .class);

    @Autowired
    final RestaurantDao restaurantDao;
    private ImageDao imageDao;
    private MenuDao menuDao;

@Inject
    public RestaurantController(RestaurantDao restaurantDao, ImageDao imageDao, MenuDao menuDao) {
        this.restaurantDao = restaurantDao;
        this.imageDao = imageDao;
        this.menuDao = menuDao;
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

        for (String url : restaurant.getMenuUrls()) {
            final Menu menu = new Menu(url);
            menu.setImageUrl(url);
            menu.setRestaurant(newrestaurant);
            menuDao.create(menu);
        }

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
    public Result getRestaurantByLocation (Double lat,Double lng) {

        if (null == lat){
            return badRequest("latitude must be provided");
        }

        final Collection<Restaurant> restaurants = restaurantDao.findRestaurantByLocation(lat, lng);

        for(Restaurant restaurant_new: restaurants ){
            String[] image_strings = imageDao.getImageById(restaurant_new.getId());
            LOGGER.debug("img collection is "+ image_strings);
            restaurant_new.setImageUrls(image_strings);
        }

        for(Restaurant restaurant_new: restaurants ){
            String[] image_strings = menuDao.getMenuById(restaurant_new.getId());
            LOGGER.debug("img collection is "+ image_strings);
            restaurant_new.setMenuUrls(image_strings);
        }


        final JsonNode result = Json.toJson(restaurants);

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

        LOGGER.debug(("fhhfreufh"));
        if (null == id) {
            return badRequest("name must be provided");
        }
        //LOGGER.debug("id{}",id.toString());

        final Optional<Restaurant> restaurants = restaurantDao.read(id);
        Collection<Restaurant> restaurantArrayList = new ArrayList<>();
        restaurants.ifPresent(restaurantArrayList :: add);
        LOGGER.debug("arraylist{}",restaurantArrayList);


            for (Restaurant restaurant_new : restaurantArrayList) {
                String[] image_strings = imageDao.getImageById(restaurant_new.getId());
                LOGGER.debug("img collection is " + image_strings);
                restaurant_new.setImageUrls(image_strings);
            }

        for(Restaurant restaurant_new: restaurantArrayList ){
            String[] image_strings = menuDao.getMenuById(restaurant_new.getId());
            LOGGER.debug("img collection is "+ image_strings);
            restaurant_new.setMenuUrls(image_strings);
        }


        final JsonNode result = Json.toJson(restaurantArrayList);

        return ok(result);
    }
    @Transactional
    public Result getFilteredRestaurants() {

        final JsonNode json = request().body().asJson();
        final Restaurant restaurant = Json.fromJson(json, Restaurant.class);
        String type = restaurant.getType();
        if(type == "")
            type = " veg / non-veg";

        Integer minCost=0;
        Integer maxCost=0;
        Integer fsort=0;
        String[] cuisines = restaurant.getCuisines();
        StringBuffer finalCuisines = new StringBuffer(cuisines.length);


        for(int i=0;i<cuisines.length;i++)
        {

            if(finalCuisines.indexOf(cuisines[i]) == -1)
              finalCuisines.append(cuisines[i]+"%");
        }

       maxCost = restaurant.getCost();

        if(maxCost == 0)
            maxCost=3000;



        String sort = (json.get("sort").asText());
        if(sort == "")
            fsort = 0;
        else
          fsort = Integer.parseInt(sort);

        String time = json.get("open").asText();


//        String workinghrs = restaurant.getWorkinghrs();
//        LOGGER.debug("workinghrs{}",workinghrs);


        LOGGER.debug("type {}", type);
        LOGGER.debug("mincost {}",minCost);
        LOGGER.debug("MaxCost {}",maxCost);
        LOGGER.debug("sort {}" ,sort);
        LOGGER.debug("cuisines {}",finalCuisines);
        LOGGER.debug("time{}",time);

        LOGGER.debug("len{}",cuisines.length);

        final Collection<Restaurant> restaurants = restaurantDao.findRestaurantByFilters(type, minCost, maxCost, finalCuisines.toString(),fsort,time );


//        Collection<Restaurant> restaurantArrayList = new ArrayList<>();
//        restaurants.ifPresent(restaurantArrayList :: add);

        for(Restaurant restaurant_new: restaurants ){
            String[] image_strings = imageDao.getImageById(restaurant_new.getId());
            LOGGER.debug("img collection is "+ image_strings);
            restaurant_new.setImageUrls(image_strings);
        }

        for(Restaurant restaurant_new: restaurants ){
            String[] image_strings = menuDao.getMenuById(restaurant_new.getId());
            LOGGER.debug("img collection is "+ image_strings);
            restaurant_new.setMenuUrls(image_strings);
        }


        final JsonNode result = Json.toJson(restaurants);

        return ok(result);
    }


    @Transactional
    public Result avgRatings(Integer rid) {

        if(null == rid)
            return badRequest("rid must be provided");

        Double avg = restaurantDao.averageRating(rid);

        if(avg==null)
            return badRequest("avg is null");

        final JsonNode result = Json.toJson(avg);

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

        for(Restaurant restaurant_new: restaurants ){
            String[] image_strings = menuDao.getMenuById(restaurant_new.getId());
            LOGGER.debug("img collection is "+ image_strings);
            restaurant_new.setMenuUrls(image_strings);
        }

        final JsonNode result = Json.toJson(restaurants);

       return ok(result);
    }


}
