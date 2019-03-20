package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.RestaurantDao;
import dao.ReviewDao;
import dao.ReviewDaoImpl;
import dao.UserDao;
import models.Restaurant;
import models.Review;
import models.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

public class ReviewController extends Controller {

    private final static Logger.ALogger LOGGER = Logger.of(ReviewController .class);

    final ReviewDao reviewDao;
    final RestaurantDao restaurantDao;

    @Inject
    public ReviewController(ReviewDao reviewDao, RestaurantDao restaurantDao) {
        this.reviewDao = reviewDao;
        this.restaurantDao = restaurantDao;
    }

    @Transactional
    public Result createReview() {

        final JsonNode json = request().body().asJson();

        final Review review = Json.fromJson(json, Review.class);

        LOGGER.debug("Review name = " + review.getRevId());
        LOGGER.error("This is an error");

        final Integer restaurantId = json.get("restaurant_Id").asInt();
        final Optional<Restaurant> restaurant =  restaurantDao.read(restaurantId);

        if (!restaurant.isPresent()) {
            return badRequest();
        }

        review.setRestaurant(restaurant.get());

        if (null == review.getReview()) {
            return badRequest("Review must be provided");
        }


        reviewDao.create(review);
        final JsonNode result = Json.toJson(review);
        return ok(result);





    }

//
//    @Transactional
//    public Result findReviews(Restaurant restaurant) {
//
//        if (null == restaurant) {
//            return badRequest("rid must be provided");
//        }
//
//        final Integer rid=restaurant.getId();
//        final Collection<Review> reviews = reviewDao.findReviews(rid);
////        if(reviews.isPresent()) {
////            final JsonNode result = Json.toJson(restaurants.get());
////            return ok(result);
////        } else {
////            return notFound();
////        }
//        final JsonNode result = Json.toJson(reviews);
//
//        return ok(result);
//    }
////
//
    @Transactional
    public Result findRatings(Integer rid) {
        if (null == rid) {
            return badRequest("rid must be provided");
        }

        final Collection<Review> rating = reviewDao.findRatings(rid);
//        if(rating.isPresent()) {
//            final JsonNode result = Json.toJson(restaurants.get());
//            return ok(result);
//        } else {
//            return notFound();
//        }

        if(rating == null)
            return badRequest("Rid not found");

        final JsonNode result = Json.toJson(rating);
        return ok(result);
    }


}
