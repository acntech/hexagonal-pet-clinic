package no.acntech.hexapetclinic.infra.persistence.jpa.entity.framework;

import jakarta.persistence.MappedSuperclass;

/**
 * Superclass for Immutable entities - no 'updatedAt' attribute.
 */
@MappedSuperclass
public abstract class ImmutableJpaEntity extends JpaEntity {

}
