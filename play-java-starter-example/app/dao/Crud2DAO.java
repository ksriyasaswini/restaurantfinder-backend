package dao;


import java.util.Collection;

public interface Crud2DAO<Entity ,Key> {
    Entity create(Entity entity);
    //Optional<Entity> read(Key id);
     Entity update(Entity entity);
    // Entity delete(Key id);
    Collection<Entity> all();

}