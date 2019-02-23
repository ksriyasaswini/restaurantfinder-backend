package dao;

import models.UserDetails;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.Collection;

public class UserDaoImpl implements UserDao {

    @Inject
    public UserDaoImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    final JPAApi jpaApi;


    public UserDetails create (UserDetails userDetails) {

        if (null == userDetails) {
            throw new IllegalArgumentException("ID must be provided");
        }

        jpaApi.em().persist(userDetails);
        return userDetails;

    }

    @Override
    public Collection<UserDetails> all() {
        return null;
    }


    @Override
    public UserDetails findUserByName(String username) {

        TypedQuery<UserDetails> query = jpaApi.em().createQuery("SELECT u from UserDetails u where username = '"+username+"'", UserDetails.class);
        UserDetails existingUser = query.getSingleResult();

        if(null == existingUser) {
            return null;
        }
        return existingUser;
        //return null;

    }

    @Override
    public UserDetails findUserByAuthToken(String Token) {

        TypedQuery<UserDetails> query = jpaApi.em().createQuery("SELECT u from UserDetails u where AccessToken = '"+Token+"'", UserDetails.class);
        UserDetails userDetails = query.getSingleResult();

        if(null == userDetails) {
            return null;
        }
        return userDetails;
    }

    public UserDetails update(UserDetails userDetails) {

        if (null == userDetails) {
            throw new IllegalArgumentException("Book must be provided");
        }

        if (null == userDetails.getId()) {
            throw new IllegalArgumentException("Book id must be provided");
        }

        final UserDetails existingUser = jpaApi.em().find(UserDetails.class, userDetails.getId());
        if (null == existingUser) {
            return null;
        }

        //existingBook.setTitle(book.getTitle());
        userDetails.setAccessToken(userDetails.getAccessToken());

        jpaApi.em().persist(userDetails);

        return userDetails;
    }




}
