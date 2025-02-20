package no.acntech.hexapetclinic.infra.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import no.acntech.hexapetclinic.utils.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Generic response wrapper containing data and metadata.
 *
 * @param <T> the type of data contained in the response
 */
@Schema(description = "Generic response wrapper containing data and metadata.")
@Getter
public class ResponseDto<T> {

  public static final String TIMESTAMP_KEY = "timestamp";

  @Schema(description = "The actual response data.")
  private final T data;

  @Schema(description = "Metadata associated with the response, including a timestamp.",
      example = "{ \"timestamp\": \"2024-12-01T12:00:00Z\" }")
  private final Map<String, Object> meta = new HashMap<>();

  public ResponseDto() {
    this(null);
  }

  public ResponseDto(T data) {
    this.data = data;
    this.meta.put(TIMESTAMP_KEY, Instant.now());
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("meta\n", CollectionUtils.prettyPrint(meta))
        .append("data", data)
        .toString();
  }
}
