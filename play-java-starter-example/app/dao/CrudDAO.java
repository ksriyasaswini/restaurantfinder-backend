package dao;

import java.util.Collection;
import java.util.Optional;

public interface CrudDAO<Entity ,Key> {
    Entity create(Entity entity);
    Optional<Entity> read(Key id);
    Entity update(Entity entity);
    Entity delete(Key id);
   Collection<Entity> all();

}
