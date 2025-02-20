package no.acntech.hexapetclinic.infra.persistence.jpa.repository;

import java.util.List;
import no.acntech.hexapetclinic.infra.persistence.jpa.entity.OwnerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations and custom queries
 * on the {@code OwnerJpaEntity} entity.
 *
 * This interface extends {@code JpaRepository}, thus providing JPA related
 * methods out of the box. It acts as a Data Access Object (DAO) for the
 * {@code OwnerJpaEntity} class.
 */
@Repository
public interface OwnerJpaRepository extends JpaRepository<OwnerJpaEntity, Long> {

  List<OwnerJpaEntity> findByLastName(String lastName);

}