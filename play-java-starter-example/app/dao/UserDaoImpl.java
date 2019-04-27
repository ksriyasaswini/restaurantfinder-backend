package dao;

import models.UserDetails;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Optional;

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
    public Optional<UserDetails> read(Integer id) {

        if(null == id) {
            throw new IllegalArgumentException("Id must be provided");
        }

        final UserDetails user = jpaApi.em().find(UserDetails.class, id);
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Collection<UserDetails> all() {
        return null;
    }


    @Override
    public UserDetails findUserByName(String username) {

        TypedQuery<UserDetails> query1 = jpaApi.em().createQuery("SELECT u from UserDetails u where username = '"+username+"'", UserDetails.class);
        UserDetails existingUser = query1.getSingleResult();

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

    @Override
    public Optional<UserDetails> readbyname(UserDetails userDetails) {
         UserDetails userDetails_final = null;
        if (null == userDetails) {
            throw new IllegalArgumentException("Id must be provided");
        }


             userDetails_final = jpaApi.em().find(UserDetails.class, userDetails.getId());
            return userDetails_final != null ? Optional.of(userDetails_final) : Optional.empty();


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

    @Override
    public UserDetails delete(Integer id) {
        return null;
    }


}
