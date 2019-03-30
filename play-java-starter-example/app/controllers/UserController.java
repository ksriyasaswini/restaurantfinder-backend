package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.BaseEncoding;

import dao.*;
import controllers.RestaurantController;
import io.jsonwebtoken.impl.crypto.MacProvider;
import models.Favourites;
import models.Restaurant;
import models.UserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import play.Logger;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import java.lang.String;

import play.mvc.*;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class UserController extends Controller {

    private final static Logger.ALogger LOGGER = Logger.of(UserController.class);
    private final static int HASH_ITERATIONS = 100;


    RestaurantController restaurantController;
    @Inject
    public UserController(JPAApi jpaApi, UserDao userDao, FavouritesDao favouritesDao, RestaurantDao restaurantDao,ImageDao imageDao, MenuDao menuDao) {
        this.jpaApi = jpaApi;
        this.userDao = userDao;
        this.favouritesDao = favouritesDao;
        this.restaurantDao = restaurantDao;
        this.imageDao = imageDao;
        this.menuDao = menuDao;
    }

    final JPAApi jpaApi;




    @Autowired
    final UserDao userDao;
    public ImageDao imageDao;
    private FavouritesDao favouritesDao;
    private RestaurantDao restaurantDao;
    private MenuDao menuDao;


    @Transactional
    public Result createUserDetails() {

        final JsonNode json = request().body().asJson();

        final UserDetails userDetails = Json.fromJson(json, UserDetails.class);

        if (null == userDetails.getUsername()) {
            return badRequest("Username  must be provided");
        }


        final String password = json.get("password").asText();
        if (null == password) {
            return badRequest("Missing mandatory parameters");
        }

        final String salt = generateSalt();

        final String hash = generateHash(salt, password, HASH_ITERATIONS);

        userDetails.setHashIterations(HASH_ITERATIONS);
        userDetails.setSalt(salt);
        userDetails.setPasswordHash(hash);

        userDao.create(userDetails);

        final JsonNode result = Json.toJson(userDetails);

        return ok(result);
        //return null;
    }

    @Transactional
    public Result updateUserDetails() {


        final JsonNode json = request().body().asJson();

        final Favourites favourites = Json.fromJson(json, Favourites.class);
        final String accessToken = json.get("Token").asText();
        final Integer fav=json.get("favourites").asInt();
        LOGGER.debug(accessToken);
        final UserDetails userDetails=  userDao.findUserByAuthToken(accessToken);

            favourites.setFavres(fav);
            favourites.setUser(userDetails);
            LOGGER.debug("user details {}",userDetails);
            LOGGER.debug("fav {}",favourites.getFavres());
            LOGGER.debug("id {}",fav);
            final Optional<Restaurant> restaurants = restaurantDao.read(fav);
            if(!restaurants.isPresent()) {
                return  badRequest("Restaurant does not exists");
            }


            favourites.setRestaurant(restaurants.get());
            favouritesDao.create(favourites);


        final JsonNode result = Json.toJson(favourites);

        return ok(result);
    }

        private String generateSalt() {

            //return "ABC";

            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 3) { // length of the random string.
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            String saltStr = salt.toString();
            return saltStr;
        }



    private String generateHash(String salt, String password, int iterations) {
        try {

            final String contat = salt + ":" + password;

            // TODO Run in a loop x iterations
            // TODO User a better hash function
            final MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(contat.getBytes());
            final String passwordHash = BaseEncoding.base16().lowerCase().encode(messageDigest);

            LOGGER.debug("Password hash {}", passwordHash);

            return passwordHash;
        } catch (NoSuchAlgorithmException e) {
            //e.printStackTrace();
            return null;
        }
    }


    @Transactional
    public Result signInUser() {

        final JsonNode json = request().body().asJson();

        final String username = json.get("username").asText();
         final String password = json.get("password").asText();
        if (null == password || null == username) {
            return badRequest("Missing mandatory parameters");
        }

        final UserDetails existingUser = userDao.findUserByName(username);

        if (null == existingUser) {
            return unauthorized("Wrong username");
        }

        final String salt = existingUser.getSalt();
        final int iterations = existingUser.getHashIterations();
        final String hash = generateHash(salt, password, iterations);

        if (!existingUser.getPasswordHash().equals(hash)) {
            return unauthorized("Wrong password");
        }

        existingUser.setAccessToken(generateAccessToken());

        userDao.update(existingUser);

        final JsonNode result = Json.toJson(existingUser);

        return ok(result);
    }



    private String generateAccessToken() {

        // TODO Generate a random string of 20 (or more characters)

        String Token = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder at = new StringBuilder();
        Random rnd = new Random();
        while (at.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * Token.length());
            at.append(Token.charAt(index));
        }
        String AccessToken = at.toString();
        return AccessToken;
    }

    @Transactional
    public Result UserProfile(String Token) {


        Optional<Restaurant> restaurants=null;
        Collection<Restaurant> restaurantArrayList = new ArrayList<>();

        if (null == Token ) {

            return badRequest("Missing mandatory parameters");
        }
        final UserDetails user = userDao.findUserByAuthToken(Token);
        Integer uid= user.getId();

        Integer[] favs= favouritesDao.FavouriteById(uid);
        LOGGER.debug("favs {}",favs[2]);


        for(int i=0;i<favs.length;i++) {
            restaurants = restaurantDao.read(favs[i]);

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


        }

      Map<String, Object> map = new HashMap<>();
       map.put("user", user);
       map.put("favorites", restaurantArrayList);

        final JsonNode result = Json.toJson(map.entrySet().toArray());

        return ok(result);
    }


    @Transactional
    public Result removeFavourite(Integer id) {

        if (null == id ) {

            return badRequest("Missing mandatory parameters");
        }

        else {
            TypedQuery<Favourites> query = jpaApi.em().createQuery("SELECT f from Favourites f where favres = '"+id+"'", Favourites.class);
            Favourites existingfavourites = query.getSingleResult();
            Integer fid= existingfavourites.getFid();


            final Favourites favourites = favouritesDao.delete(fid);

            final JsonNode result = Json.toJson(favourites);
            return ok(result);
        }

    }


    @Transactional
    public Result signOutUser(String Token) {

        if (null == Token ) {

            return badRequest("Missing mandatory parameters");
        }

        final UserDetails user = userDao.findUserByAuthToken(Token);

        if (null == user) {
            return unauthorized("Wrong username");
        }

        user.setAccessToken(null);

        userDao.update(user);

        return ok();
    }

}

