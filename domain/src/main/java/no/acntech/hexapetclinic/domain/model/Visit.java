package no.acntech.hexapetclinic.domain.model;

import java.time.Instant;
import no.acntech.hexapetclinic.domain.model.framework.Entity;

/**
 * Represents a visit for a pet in the domain model. A visit is associated with a specific pet
 * and includes details such as the time of the visit, a description of the visit, and methods
 * to retrieve or modify this information.
 *
 * The Visit interface provides functionality for:
 * - Retrieving the Pet associated with the visit.
 * - Accessing the time at which the visit occurred.
 * - Getting or changing the description of the visit.
 *
 * Implementations of this interface are expected to provide the necessary behavior
 * for managing pet visits within the domain context.
 */
public interface Visit extends Entity<Long> {

  Pet getPet();

  Instant getTime();

  String getDescription();

  Visit changeDescription(String description);
}
