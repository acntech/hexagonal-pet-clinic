package no.acntech.hexapetclinic.infra.persistence.jpa.repository;

import java.util.List;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.VisitJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link VisitJpaEntity} persistence.
 * This repository provides data access to Visit entities and supports operations
 * such as saving, deleting, and querying the visits stored in the database.
 *
 * Extends the {@link JpaRepository} interface to inherit basic CRUD operations.
 *
 * Features:
 * - Defines a custom query method for finding visits based on the associated pet identifier.
 *
 * Methods:
 * - {@link #findByPetId(PetIdentifier)}: Retrieves a list of visits for a specific pet.
 */
@Repository
public interface VisitJpaRepository extends JpaRepository<VisitJpaEntity, Long> {

  List<VisitJpaEntity> findByPetId(PetIdentifier petIdentifier);

}