package no.acntech.hexapetclinic.domain.model;

import lombok.Getter;

/**
 * Represents the gender of an entity.
 * Provides predefined constants for MALE and FEMALE, each associated with an integer code.
 * Includes utility methods to map integer codes to the corresponding Gender values.
 */
@Getter
public enum Gender {
  MALE(1),
  FEMALE(2);

  private final int code;

  Gender(int code) {
    this.code = code;
  }

  public static Gender fromCode(int code) {
    for (Gender gender : Gender.values()) {
      if (gender.code == code) {
        return gender;
      }
    }
    throw new IllegalArgumentException("Invalid gender code: " + code);
  }
}