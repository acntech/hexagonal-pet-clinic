package no.acntech.hexapetclinic.infra.persistence.mongo.repository.adapter;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.framework.Entity;
import no.acntech.hexapetclinic.domain.repository.BaseRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract repository implementation for MongoDB repositories.
 * Delegates read and write operations to a Spring Data MongoDB repository.
 *
 * @param <T>  Entity type (Domain model)
 * @param <E>  Entity type (Persistence model)
 * @param <ID> Entity ID type
 * @param <R>  Spring Data MongoDB repository type
 */
public abstract class AbstractMongoRepositoryAdapter<T extends Entity<ID>, E extends T, ID, R extends MongoRepository<E, ID>>
    implements BaseRepository<T, ID> {

  /**
   * Delegate Spring Data MongoDB repository used by this AbstractJpaRepositoryAdapter.
   */
  protected final R mongoRepository;

  /**
   * Protected constructor for AbstractJpaRepositoryAdapter which initializes the repository.
   *
   * @param mongoRepository the Spring Data MongoDB repository to be used; must not be null
   */
  protected AbstractMongoRepositoryAdapter(@NonNull R mongoRepository) {
    this.mongoRepository = mongoRepository;
  }

  /**
   * Checks if an entity with the given identifier exists in the repository.
   *
   * @param id The identifier to check for. Must not be null.
   * @return true if an entity with the given identifier exists, false otherwise.
   */
  @Override
  public boolean existsById(@NonNull ID id) {
    return mongoRepository.existsById(id);
  }

  /**
   * Finds an entity by its identifier.
   *
   * @param id The identifier of the entity to find. Must not be null.
   * @return An Optional containing the found entity, or empty if no entity was found with the given identifier.
   */
  @Override
  public Optional<T> findById(@NonNull ID id) {
    return mongoRepository.findById(id).map(entity -> (T) entity); // Handle casting
  }

  /**
   * Retrieves all the entities of type T from the repository.
   *
   * @return a List containing all entities of type T.
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<T> findAll() {
    return (List<T>) mongoRepository.findAll();
  }

  /**
   * Saves the given entity and returns the saved instance.
   * Delegates the save operation to the underlying Spring Data MongoDB repository.
   *
   * @param entity the entity to be saved; must not be null
   * @return the saved entity
   */
  @Override
  @Transactional
  @SuppressWarnings("unchecked")
  public T save(@NonNull T entity) {
    return (T) mongoRepository.save((E) entity);
  }

  /**
   * Deletes an entity by its identifier.
   *
   * @param id The identifier of the entity to delete. Must not be null.
   */
  @Override
  @Transactional
  public void deleteById(@NonNull ID id) {
    mongoRepository.deleteById(id);
  }

  /**
   * MongoDB does not support `refresh()` like JPA's EntityManager.
   * This method is left unimplemented because MongoDB operates with detached documents.
   *
   * @param entity the entity to refresh (not used in MongoDB)
   */
  @Override
  public void refresh(@NonNull T entity) {
//    throw new UnsupportedOperationException("MongoDB does not support refresh(). Consider reloading from the database.");
  }

  /**
   * No-op method for refreshing child entities (MongoDB does not support cascading refresh).
   *
   * @param entity the entity whose child entities should be refreshed
   */
  protected void refreshChildEntitiesOnSave(@NonNull E entity) {
    // No-op, as MongoDB does not support entity refreshes
  }
}
