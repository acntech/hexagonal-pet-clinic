package no.acntech.hexapetclinic.infra.persistence.mongo.entity.framework;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.beans.ConstructorProperties;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.model.framework.Entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.Instant;

/**
 * Convenient superclass for MongoDB entities - using a String as primary key.
 */
@Getter
@Slf4j
@NoArgsConstructor
public abstract class MongoEntity implements Entity<Long> {

    private static final int INITIAL_ODD_NUMBER = 17;
    private static final int MULTIPLIER_ODD_NUMBER = 37;

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Id
    private Long id = MongoIdGenerator.generateId();

    @CreatedDate
    private Instant createdAt = Instant.now();

    @ConstructorProperties({"id", "createdAt"})
    public MongoEntity(Long id, Instant createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    @Override
    public void validate() {
        Set<ConstraintViolation<Entity<?>>> constraintViolations = VALIDATOR.validate(this);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoEntity that = (MongoEntity) o;
        return new EqualsBuilder().append(id, that.id).isEquals();
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
