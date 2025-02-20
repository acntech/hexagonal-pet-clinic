package no.acntech.hexapetclinic.domain.model.framework;

/**
 * Identifiable is a marker interface representing entities that can be uniquely identified by an ID.
 * Classes implementing this interface are expected to provide a mechanism to retrieve a unique identifier.
 *
 * @param <ID> the type of the identifier, typically a scalar type such as {@code String}, {@code Long}, or {@code UUID}.
 */
@SuppressWarnings("PMD.GenericsNaming")
public interface Identifiable<ID> {

  ID getId();
}
