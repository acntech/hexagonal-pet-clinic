package no.acntech.hexapetclinic.domain.service;

import java.time.Instant;
import java.time.LocalDate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.connector.PetDescriptionEnhancer;
import no.acntech.hexapetclinic.domain.factory.OwnerFactory;
import no.acntech.hexapetclinic.domain.factory.VisitFactory;
import no.acntech.hexapetclinic.domain.model.*;
import no.acntech.hexapetclinic.domain.repository.EntityNotFoundException;
import no.acntech.hexapetclinic.domain.repository.OwnerRepository;
import no.acntech.hexapetclinic.domain.repository.PetRepository;
import no.acntech.hexapetclinic.domain.repository.VisitRepository;

/**
 * The RegistrationService class provides functionality for registering owners, pets, and visits
 * within the system. It acts as an intermediary between the domain model and persistence layers,
 * while also enabling domain-specific operations such as description enhancement and entity validation.
 *
 * Responsibilities:
 * - Registering new owners, including their personal information such as name, address, contact details, etc.
 * - Registering new pets under an existing owner, including detailed attributes like type, breed, gender, birthdate,
 *   and an enhanced description.
 * - Registering visits for pets, including visit time and description.
 *
 * Dependencies:
 * - OwnerFactory: Used to construct new instances of {@link Owner}.
 * - VisitFactory: Used to create new instances of {@link Visit}.
 * - PetRepository: Handles persistence operations for {@link Pet} entities.
 * - OwnerRepository: Handles persistence operations for {@link Owner} entities.
 * - VisitRepository: Handles persistence operations for {@link Visit} entities.
 * - PetDescriptionEnhancer: Enhances pet descriptions based on type, breed, and context.
 *
 * Key methods:
 * - {@code registerOwner}: Creates and persists a new owner in the repository.
 * - {@code registerPet}: Adds a new pet under an existing owner and persists it in the repository after enhancing its description.
 * - {@code registerVisit}: Records a new visit for a pet and persists it in the repository.
 */
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

  private final OwnerFactory ownerFactory;
  private final VisitFactory visitFactory;

  private final PetRepository petRepository;
  private final OwnerRepository ownerRepository;
  private final VisitRepository visitRepository;

  private final PetDescriptionEnhancer descrEnhancer;

  /**
   * Registers a new owner by creating an Owner object with the provided details
   * and saving it to the owner repository.
   *
   * @param firstName the first name of the owner
   * @param lastName the last name of the owner
   * @param address the address of the owner
   * @param city the city of the owner
   * @param telephone the telephone number of the owner
   * @param email the email address of the owner
   * @return the saved Owner instance
   */
  @NonNull
  public Owner registerOwner(
      String firstName,
      String lastName,
      String address,
      String city,
      TelephoneNumber telephone,
      EmailAddress email
  ) {
    Owner owner = ownerFactory.createOwner(
        firstName,
        lastName,
        address,
        city,
        telephone,
        email
    );

    return ownerRepository.save(owner);
  }

  /**
   * Registers a new pet under a specified owner.
   * This method creates a new pet instance and associates it with an owner.
   * The provided description can be enhanced based on the pet's type and breed.
   *
   * @param name the name of the pet; must not be null
   * @param type the type of the pet (e.g., DOG, CAT); must not be null
   * @param breed the breed of the pet; must not be null
   * @param gender the gender of the pet (e.g., MALE, FEMALE); must not be null
   * @param birthDate the birth date of the pet; must not be null
   * @param description a description of the pet; can be null
   * @param ownerId the unique identifier of the owner to whom the pet will be registered; must not be null
   * @return the newly registered pet instance
   * @throws IllegalArgumentException if any mandatory parameter is null or invalid
   * @throws EntityNotFoundException if the owner with the given ownerId does not exist
   */
  @NonNull
  public Pet registerPet(
      @NonNull String name,
      @NonNull PetType type,
      @NonNull String breed,
      @NonNull Gender gender,
      @NonNull LocalDate birthDate,
      String description,
      @NonNull Long ownerId
  ) {
    Owner owner = ownerRepository.findByIdOrElseThrow(ownerId);

    log.debug("Enhancing description for pet type: {}, breed: {}", type, breed);
    description = descrEnhancer.enhanceDescription(type, breed, description);
    log.debug("Enhanced description: {}\n\n", description);

    Pet newPet = owner.registerPet(
        name,
        type,
        breed,
        gender,
        birthDate,
        description
    );

    return petRepository.save(newPet);
  }

  /**
   * Registers a new visit for a given pet.
   *
   * @param petIdentifier the unique identifier of the pet for whom the visit is being registered; must not be null
   * @param time the timestamp of the visit; must not be null
   * @param description the description or reason for the visit; must not be null
   * @return the saved instance of the registered visit; never null
   */
  @NonNull
  public Visit registerVisit(@NonNull String petIdentifier, @NonNull Instant time, @NonNull String description) {
    Pet pet = petRepository.findByIdentifier(PetIdentifier.of(petIdentifier));

    Visit visit = visitFactory.createVisit(
        pet,
        time,
        description
    );

    return visitRepository.save(visit);
  }

}