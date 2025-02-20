package no.acntech.hexapetclinic.infra.persistence.jpa.entity.framework;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.Instant;
import java.util.Set;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.model.framework.Entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.actuate.audit.listener.AuditListener;
import org.springframework.data.annotation.CreatedDate;

/**
 * Convenient superclass for JPA entities - using a sequenced Long as primary key.
 */
@MappedSuperclass
@EntityListeners(AuditListener.class)
@Slf4j
@Getter
public abstract class JpaEntity implements Entity<Long> {

  protected static final String TIMESTAMP_COLUMN_DEFINITION = "TIMESTAMP WITH TIME ZONE NOT NULL";

  private static final int INITIAL_ODD_NUMBER = 17;
  private static final int MULTIPLIER_ODD_NUMBER = 37;

  private static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = TIMESTAMP_COLUMN_DEFINITION)
  @CreatedDate
  private Instant createdAt = Instant.now();

  @Override
  public void validate() {
    Set<ConstraintViolation<Entity<?>>> constraintViolations = VALIDATOR.validate(this);
    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) { // Ensures exact class match
      return false;
    }
    JpaEntity jpaEntity = (JpaEntity) o;
    return new EqualsBuilder().append(id, jpaEntity.id).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(INITIAL_ODD_NUMBER, MULTIPLIER_ODD_NUMBER)
        .append(id)
        .toHashCode();
  }


  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("id", id)
        .append("createdAt", createdAt)
        .toString();
  }
}
