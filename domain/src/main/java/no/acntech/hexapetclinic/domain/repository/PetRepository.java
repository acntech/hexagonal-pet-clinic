package no.acntech.hexapetclinic.domain.repository;

import java.util.List;
import lombok.NonNull;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;

public interface PetRepository extends BaseRepository<Pet, Long> {

  /**
   * Finds and returns a pet entity by its unique pet identifier.
   *
   * @param petIdentifier the unique identifier of the pet to find; must not be null
   * @return the pet associated with the given identifier, or throws an exception if no pet is found with the given identifier
   * @throws EntityNotFoundException if no pet is found with the given identifier
   */
  @NonNull
  Pet findByIdentifier(@NonNull PetIdentifier petIdentifier);

  /**
   * Finds all pets that match the given name.
   *
   * @param name The name of the pet(s) to search for; must not be null.
   * @return A list of pets with the specified name, or an empty list if no pets match the given name.
   */
  @NonNull
  List<Pet> findByName(@NonNull String name);

}
