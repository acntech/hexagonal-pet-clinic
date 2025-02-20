package no.acntech.hexapetclinic.infra.config.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServletAttributeNames {

  public static final String REQUEST_TIMESTAMP = "req-ts";
  public static final String RESPONSE_TIMESTAMP = "res-ts";
}
