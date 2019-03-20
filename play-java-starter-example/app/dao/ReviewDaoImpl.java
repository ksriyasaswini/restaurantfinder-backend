package dao;

import models.Restaurant;
import models.Review;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class ReviewDaoImpl implements ReviewDao{

    final JPAApi jpaApi;

    @Inject
    public ReviewDaoImpl(JPAApi jpaApi) {

        this.jpaApi = jpaApi;

    }


    public Review create(Review review) {

        if(null == review) {
            throw new IllegalArgumentException("Review must be provided");
        }

        jpaApi.em().persist(review);

        return review;

    }

    @Override
    public Optional<Review> read(Integer id) {
        return Optional.empty();
    }

    @Override
    public Review update(Review review) {
        return null;
    }

    @Override
    public Review delete(Integer id) {
        return null;
    }

    @Override
    public Collection<Review> all() {
        TypedQuery<Review> query = jpaApi.em().createQuery("SELECT r FROM Review r", Review.class);
        List<Review> reviews= query.getResultList();

        return reviews;
    }

    @Override
    public Collection<Review> findReviews(Integer rid) {

        TypedQuery<Review> query = jpaApi.em().createQuery("SELECT r FROM Review r JOIN Restaurant re ON r.rid=re.Id where fk_Id LIKE '%"+rid+"%'", Review.class);
        List<Review> reviews= query.getResultList();

        return reviews;
    }

    @Override
    public Collection<Review> findRatings(Integer rid){
        TypedQuery<Review> query = jpaApi.em().createQuery("SELECT r FROM Review r where restaurant_Id LIKE '%"+rid+"%'", Review.class);
        List<Review> ratings= query.getResultList();


        return ratings;
    }


}