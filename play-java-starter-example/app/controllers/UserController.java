package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.BaseEncoding;
import dao.UserDao;
import models.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserController extends Controller {

    private final static Logger.ALogger LOGGER = Logger.of(UserController.class);
    private final static int HASH_ITERATIONS = 100;

    @Autowired
    final UserDao userDao;

    @Inject
    public UserController(UserDao userDao) { this.userDao = userDao; }

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

        private String generateSalt() {

            return "ABC";
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


//    public Result signInUser() {
//
//        final JsonNode json = request().body().asJson();
//
//        final String username = json.get("username").asText();
//        final String password = json.get("password").asText();
//        if (null == password || null == username) {
//            return badRequest("Missing mandatory parameters");
//        }
//
//        final UserDetails existingUser = userDao.findUserByName(username);
//
//        if (null == existingUser) {
//            return unauthorized("Wrong username");
//        }
//
//        final String salt = existingUser.getSalt();
//        final int iterations = existingUser.getHashIterations();
//        final String hash = generateHash(salt, password, iterations);
//
//        if (!existingUser.getPasswordHash().equals(hash)) {
//            return unauthorized("Wrong password");
//        }
//
//        existingUser.setAccessToken(generateAccessToken());
//
//        //userDao.update(existingUser);
//
//        final JsonNode result = Json.toJson(existingUser);
//
//        return ok(result);
//    }
//
//    private String generateAccessToken() {
//
//        // TODO Generate a random string of 20 (or more characters)
//
//        return "ABC1234";
//    }











//    public Result getValuesById(Integer id) {
//
//        if (null == id) {
//            return badRequest("Id must be provided");
//        }
//
//        final UserDetails loginDetails = loginDetailsMap.get(id);

//        if (null == loginDetails) {
//            return notFound();
//        }
//
//        final JsonNode result = Json.toJson(loginDetails);
//
//        return ok(result);
//    }
//
//
//
//    public Result deleteValuesById(Integer id) {
//
//        // TODO Missing implementation
//
//        return ok("Delete details");
//    }
//
//    public Result getAllValues() {
//
//        final JsonNode result = Json.toJson(loginDetailsMap.values());
//
//        return ok(result);
//    }

}
