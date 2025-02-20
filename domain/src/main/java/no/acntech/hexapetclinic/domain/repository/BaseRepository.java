package no.acntech.hexapetclinic.domain.repository;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.framework.Entity;

/**
 * Base repository interface combining read-only and modifiable repository interfaces.
 */
@SuppressWarnings("PMD.GenericsNaming")
public interface BaseRepository<T extends Entity<ID>, ID> {

  /**
   * Checks if an entity with the given identifier exists in the repository.
   *
   * @param id The identifier to check for. Must not be null.
   * @return true if an entity with the given identifier exists, false otherwise.
   */
  default boolean existsById(@NonNull ID id) {
    return findById(id).isPresent();
  }

  /**
   * Finds an entity by its identifier.
   *
   * @param id The identifier of the entity to find. Must not be null.
   * @return An Optional containing the found entity, or empty if no entity was found with the given identifier.
   * @throws EntityNotFoundException if no entity was found with the given identifier
   */
  Optional<T> findById(@NonNull ID id);

  default T findByIdOrElseThrow(@NonNull ID id) {
    return findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found: " + id));
  }

  /**
   * Retrieves all the entities of type T.
   *
   * @return a List containing all entities of type T.
   */
  List<T> findAll();


  /**
   * Saves the given entity and returns the saved instance.
   *
   * @param entity the entity to be saved; must not be null
   * @return the saved entity
   */
  T save(@NonNull T entity);

  /**
   * Deletes an entity with the specified ID from the repository.
   *
   * @param id the ID of the entity to be deleted, must not be null
   */
  void deleteById(@NonNull ID id);


  /**
   * Refreshes the state of the given entity to ensure it is synchronized with the underlying data store.
   *
   * @param entity the entity whose state needs to be refreshed; must not be null
   */
  void refresh(@NonNull T entity);

}
