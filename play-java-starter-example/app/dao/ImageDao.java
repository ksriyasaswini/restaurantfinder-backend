package dao;

import models.Images;

import java.util.Collection;

public interface ImageDao extends CrudDAO<Images,String>{

    String[]getImageById(Integer id);
}
