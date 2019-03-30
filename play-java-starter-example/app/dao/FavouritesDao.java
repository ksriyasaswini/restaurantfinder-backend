package dao;

import models.Favourites;
import models.Images;
import models.Restaurant;

import java.util.Collection;

public interface FavouritesDao extends CrudDAO<Favourites,Integer>{

    Integer[] FavouriteById(Integer id);


}
