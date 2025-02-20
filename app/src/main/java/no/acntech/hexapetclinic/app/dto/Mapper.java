package no.acntech.hexapetclinic.app.dto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects.OwnerResponseDto;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects.PetResponseDto;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects.VisitResponseDto;
import no.acntech.hexapetclinic.domain.model.Owner;
import no.acntech.hexapetclinic.domain.model.Pet;
import no.acntech.hexapetclinic.domain.model.Visit;

@RequiredArgsConstructor
public final class Mapper {

  public static OwnerResponseDto toOwnerResponseDto(@NonNull Owner owner) {
    return new OwnerResponseDto(
        owner.getId(),
        owner.getFirstName(),
        owner.getLastName(),
        owner.getAddress(),
        owner.getCity(),
        owner.getTelephone().getPrimitive(),
        owner.getEmail().getPrimitive()
    );
  }

  public static VisitResponseDto toVisitResponseDto(@NonNull Visit visit) {
    return new VisitResponseDto(
        visit.getId(),
        visit.getPet().getIdentifier().getPrimitive(),
        visit.getTime(),
        visit.getDescription()
    );
  }

  public static PetResponseDto toPetResponseDto(@NonNull Pet pet) {
    return new PetResponseDto(
        pet.getId(),
        pet.getIdentifier().getPrimitive(),
        pet.getName(),
        pet.getType().name(),
        pet.getBreed(),
        pet.getGender().name(),
        pet.getBirthDate(),
        pet.getDescription(),
        pet.getOwner().getId()
    );
  }


}