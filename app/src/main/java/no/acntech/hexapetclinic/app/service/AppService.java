package no.acntech.hexapetclinic.app.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects.PetResponseDto;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects.VisitResponseDto;
import no.acntech.hexapetclinic.app.dto.Mapper;
import no.acntech.hexapetclinic.domain.model.EmailAddress;
import no.acntech.hexapetclinic.domain.model.Owner;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.domain.model.PetType;
import no.acntech.hexapetclinic.domain.model.Gender;
import no.acntech.hexapetclinic.domain.model.TelephoneNumber;
import no.acntech.hexapetclinic.domain.model.Visit;
import no.acntech.hexapetclinic.domain.repository.OwnerRepository;
import no.acntech.hexapetclinic.domain.repository.PetRepository;
import no.acntech.hexapetclinic.domain.repository.VisitRepository;
import no.acntech.hexapetclinic.domain.service.RegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppService {

  private final RegistrationService registrationService;

  private final VisitRepository visitRepo;
  private final PetRepository petRepo;
  private final OwnerRepository ownerRepo;

  // Visit
  @Transactional
  @NonNull
  public DataTransferObjects.VisitResponseDto handleVisitCreationDto(@NonNull DataTransferObjects.VisitCreationDto dto) {
    Visit visit = registrationService.registerVisit(
        dto.petIdentifier(),
        dto.time(),
        dto.description()
    );
    return Mapper.toVisitResponseDto(visit);
  }

  public VisitResponseDto getVisitById(Long id) {
    Visit visit = visitRepo.findByIdOrElseThrow(id);

    return Mapper.toVisitResponseDto(visit);
  }

  public List<VisitResponseDto> getAllVisits() {
    List<Visit> visits = visitRepo.findAll();
    return visits.stream()
        .map(Mapper::toVisitResponseDto)
        .collect(Collectors.toList());
  }

  // Pet

  @Transactional
  @NonNull
  public DataTransferObjects.PetResponseDto handlePetCreationDto(@NonNull DataTransferObjects.PetCreationDto dto) {
    Pet pet = registrationService.registerPet(
        dto.name(),
        PetType.valueOf(dto.type().toUpperCase()),
        dto.breed(),
        Gender.valueOf(dto.gender().toUpperCase()),
        dto.birthDate(),
        dto.description(),
        dto.ownerId()
    );
    return Mapper.toPetResponseDto(pet);
  }

  public PetResponseDto getPetById(Long id) {
    Pet pet = petRepo.findByIdOrElseThrow(id);
    return Mapper.toPetResponseDto(pet);
  }

  public PetResponseDto getPetByIdentifier(String identifierValue) {
    Pet pet = petRepo.findByIdentifier(PetIdentifier.of(identifierValue));
    return Mapper.toPetResponseDto(pet);
  }

  public List<PetResponseDto> getAllPets() {
    List<Pet> pets = petRepo.findAll();
    return pets.stream()
        .map(Mapper::toPetResponseDto)
        .collect(Collectors.toList());
  }

  // Owner
  @Transactional
  @NonNull
  public DataTransferObjects.OwnerResponseDto handleOwnerCreationDto(@NonNull DataTransferObjects.OwnerCreationDto dto) {
    Owner owner = registrationService.registerOwner(
        dto.firstName(),
        dto.lastName(),
        dto.address(),
        dto.city(),
        TelephoneNumber.of(dto.telephone()),
        EmailAddress.of(dto.email())
    );
    return Mapper.toOwnerResponseDto(owner);
  }

  public DataTransferObjects.OwnerResponseDto getOwnerById(Long id) {
    Owner owner = ownerRepo.findByIdOrElseThrow(id);

    return Mapper.toOwnerResponseDto(owner);
  }

  public List<DataTransferObjects.OwnerResponseDto> getAllOwners() {
    List<Owner> owners = ownerRepo.findAll();
    return owners.stream()
        .map(Mapper::toOwnerResponseDto)
        .collect(Collectors.toList());
  }
}