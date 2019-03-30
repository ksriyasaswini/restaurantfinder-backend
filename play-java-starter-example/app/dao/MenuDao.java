package dao;

import models.Images;
import models.Menu;

import java.util.Collection;

public interface MenuDao extends CrudDAO<Menu,String>{

    String[]getMenuById(Integer id);
}
