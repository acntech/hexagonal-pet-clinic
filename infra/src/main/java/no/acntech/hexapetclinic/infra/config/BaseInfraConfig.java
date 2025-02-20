package no.acntech.hexapetclinic.infra.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseInfraConfig {

  protected Logger log = LoggerFactory.getLogger(this.getClass());
}
