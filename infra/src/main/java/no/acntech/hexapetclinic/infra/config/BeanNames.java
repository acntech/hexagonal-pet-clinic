package no.acntech.hexapetclinic.infra.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BeanNames {

  public static final String DATA_SOURCE = "dataSource";
  public static final String H2_SERVER = "h2Server";

  public static final String DETAIL_LOG_HTTP_EXCHANGE = "detailLogHttpExchange";

}
