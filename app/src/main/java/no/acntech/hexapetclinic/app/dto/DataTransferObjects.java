package no.acntech.hexapetclinic.app.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.Instant;
import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.acntech.hexapetclinic.utils.json.FlexibleInstantDeserializer;
import no.acntech.hexapetclinic.utils.json.FlexibleLocalDateDeserializer;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class DataTransferObjects {

  // --- Pet DTOs ---
  public record PetResponseDto(
      @NonNull Long id,
      @NonNull String identifier,
      @NonNull String name,
      @NonNull String type,
      @NonNull String breed,
      @NonNull String gender,
      @NonNull LocalDate birthDate,
      @NonNull String description,
      @NonNull Long ownerId
  ) {

  }

  public record PetCreationDto(
      @NonNull String name,
      @NonNull String type,
      @NonNull String breed,
      @NonNull String gender,
      @JsonDeserialize(using = FlexibleLocalDateDeserializer.class)
      @NonNull LocalDate birthDate,
      @NonNull String description,
      @NonNull Long ownerId
  ) {

  }

  // --- Visit DTOs ---
  public record VisitResponseDto(
      @NonNull Long id,
      @NonNull String petIdentifier,
      @NonNull Instant time,
      @NonNull String description
  ) {

  }

  public record VisitCreationDto(
      String petIdentifier,
      @JsonDeserialize(using = FlexibleInstantDeserializer.class)
      Instant time,
      String description
  ) {

  }

  // --- Owner DTOs ---
  public record OwnerResponseDto(
      Long id,
      String firstName,
      String lastName,
      String address,
      String city,
      String telephone,
      String email
  ) {

  }

  public record OwnerCreationDto(
      String firstName,
      String lastName,
      String address,
      String city,
      String telephone,
      String email
  ) {

  }
}