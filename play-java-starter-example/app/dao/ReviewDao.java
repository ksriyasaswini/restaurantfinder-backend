package dao;

import models.Restaurant;
import models.Review;

import java.util.Collection;

public interface ReviewDao extends CrudDAO<Review, Integer> {

//    Collection<Review> findRatings(Restaurant restaurant);
//    Collection<Review> findReviews(Restaurant restaurant);

    Collection<Review> findRatings(Integer rid);
    Collection<Review> findReviews(Integer rid);


}

