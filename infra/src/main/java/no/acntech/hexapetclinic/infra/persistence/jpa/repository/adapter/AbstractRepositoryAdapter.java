package no.acntech.hexapetclinic.infra.persistence.jpa.repository.adapter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.framework.Entity;
import no.acntech.hexapetclinic.domain.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract base class for adapting Spring Data JPA repositories to custom-defined repository interfaces.
 * Acts as a bridge between the domain-level repository interface (`BaseRepository`) and the underlying
 * persistence mechanism provided by the Spring Data JPA repository.
 *
 * This abstract class enforces common CRUD operations shared across different repositories and provides
 * base functionality that can be extended and customized for specific entity repositories.
 *
 * @param <T>  the type of the domain model entity
 * @param <E>  the type of the JPA entity that corresponds to the domain model
 * @param <ID> the type of the identifier of the entity
 * @param <R>  the type of the Spring Data JPA repository used for persistence
 */
public abstract class AbstractRepositoryAdapter<T extends Entity<ID>, E extends T, ID, R extends JpaRepository<E, ID>>
    implements BaseRepository<T, ID> {

  /**
   * Delegate Spring Data JPA repository used by this AbstractRepositoryAdapter.
   */
  protected final R jpaRepository;

  @PersistenceContext
  protected EntityManager entityManager;

  /**
   * Protected constructor for AbstractRepositoryAdapter which initializes the repository with a given Spring Data JPA repository.
   *
   * @param jpaRepository the Spring Data JPA repository to be used by this AbstractRepositoryAdapter; must not be null
   */
  protected AbstractRepositoryAdapter(@NonNull R jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  /**
   * Checks if an entity with the given identifier exists in the repository.
   *
   * @param id The identifier to check for. Must not be null.
   * @return true if an entity with the given identifier exists, false otherwise.
   */
  @Override
  public boolean existsById(@NonNull ID id) {
    return jpaRepository.existsById(id);
  }

  /**
   * Finds an entity by its identifier.
   *
   * @param id The identifier of the entity to find. Must not be null.
   * @return An Optional containing the found entity, or empty if no entity was found with the given identifier.
   */
  @Override
  public Optional<T> findById(@NonNull ID id) {
    return jpaRepository.findById(id).map(entity -> (T) entity); // Handle casting
  }

  /**
   * Retrieves all the entities of type T from the repository.
   *
   * @return a List containing all entities of type T.
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<T> findAll() {
    return (List<T>) jpaRepository.findAll();
  }

  /**
   * Saves the given entity and returns the saved instance. Delegates the save operation to the underlying Spring Data JPA repository.
   *
   * @param entity the entity to be saved; must not be null
   * @return the saved entity
   */
  @Override
  @Transactional
  @SuppressWarnings("unchecked")
  public T save(@NonNull T entity) {
    E result = jpaRepository.save((E) entity); // Cast to E only where necessary
    jpaRepository.flush(); // Ensure immediate database synchronization
    entityManager.refresh(result); // Refresh the entity to ensure it is in a consistent state
    refreshChildEntitiesOnSave(result);
    return result;
  }

  @Override
  @Transactional
  public void deleteById(@NonNull ID id) {
    jpaRepository.deleteById(id);
    jpaRepository.flush();
  }

  @Override
  public void refresh(@NonNull T entity) {
    entityManager.refresh(entity);
  }

  /**
   * Refreshes the child entities of the given entity after saving it. This method should be overridden in subclasses to provide custom
   * behavior. The default implementation is a no-op.
   *
   * @param entity the entity whose child entities should be refreshed
   */
  protected void refreshChildEntitiesOnSave(@NonNull E entity) {
    // No-op
  }
}

