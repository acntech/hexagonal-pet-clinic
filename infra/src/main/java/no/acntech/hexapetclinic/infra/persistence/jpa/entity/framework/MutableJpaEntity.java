package no.acntech.hexapetclinic.infra.persistence.jpa.entity.framework;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Superclass for mutable entities - extends JpaEntity with an 'updatedAt' attribute.
 */
@MappedSuperclass
@Getter
public abstract class MutableJpaEntity extends JpaEntity {

  @Column(columnDefinition = TIMESTAMP_COLUMN_DEFINITION)
  @LastModifiedDate
  private Instant updatedAt = Instant.now();

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("id", getId())
        .append("createdAt", getCreatedAt())
        .append("updatedAt", updatedAt)
        .toString();
  }
}
