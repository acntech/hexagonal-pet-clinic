package no.acntech.hexapetclinic.domain.model.framework;

import no.acntech.hexapetclinic.utils.validation.Validatable;

/**
 * Interface for all identifiable and validatable entity classes.
 */
public interface Entity<ID> extends Identifiable<ID>, Validatable {

  default boolean isNew() {
    return getId() == null;
  }

}
