package no.acntech.hexapetclinic.domain.repository;

import java.util.List;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.Owner;

/**
 * Repository interface for managing {@link Owner} entities. This interface extends
 * {@link BaseRepository} to inherit standard CRUD operations and adds functionality
 * to retrieve owners based on their last name.
 *
 * The {@code OwnerRepository} is expected to be implemented using a data access framework
 * such as JPA.
 *
 * @see BaseRepository
 * @see Owner
 */
public interface OwnerRepository extends BaseRepository<Owner, Long> {

  List<Owner> findByLastName(@NonNull String lastName);

}