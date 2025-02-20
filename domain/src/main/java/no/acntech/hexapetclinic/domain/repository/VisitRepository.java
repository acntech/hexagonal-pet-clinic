package no.acntech.hexapetclinic.domain.repository;

import java.util.List;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.model.Visit;

/**
 * VisitRepository is a repository interface for managing Visit entities. This interface
 * extends the BaseRepository to provide CRUD operations and includes additional
 * methods specific to Visit management.
 *
 * Responsibilities of VisitRepository include:
 * - Retrieving a list of visits associated with a specific pet ID.
 *
 * This repository operates in the domain layer and is meant to be implemented
 * using various persistence mechanisms. Typically, implementations delegate to
 * an underlying persistence layer, such as JPA.
 */
public interface VisitRepository extends BaseRepository<Visit, Long> {

  List<Visit> findByPetId(@NonNull PetIdentifier petIdentifier);

}