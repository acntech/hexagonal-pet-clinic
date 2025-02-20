package no.acntech.hexapetclinic.infra.persistence.jpa.repository;

import java.util.List;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.PetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on {@link PetJpaEntity}.
 *
 * This interface extends {@link JpaRepository} to provide standard JPA data access methods,
 * as well as custom query methods for retrieving Pet entities by specific attributes.
 *
 * Custom Query Methods:
 * - {@code findByIdentifier(PetIdentifier identifier)}: Retrieves a Pet entity based on its unique identifier.
 * - {@code findByName(String name)}: Retrieves a list of Pet entities with the specified name.
 */
@Repository
public interface PetJpaRepository extends JpaRepository<PetJpaEntity, Long> {

  PetJpaEntity findByIdentifier(@NonNull PetIdentifier identifier);

  List<PetJpaEntity> findByName(@NonNull String name);

}