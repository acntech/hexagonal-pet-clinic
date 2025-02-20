package no.acntech.hexapetclinic.infra.persistence.mongo.entity.framework;

import java.beans.ConstructorProperties;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Superclass for mutable MongoDB entities - adds an 'updatedAt' attribute.
 */
@Getter
@NoArgsConstructor
public abstract class MutableMongoEntity extends MongoEntity {

    @LastModifiedDate
    private Instant updatedAt = Instant.now();

    @ConstructorProperties({"id", "createdAt", "updatedAt"}) // Add this
    public MutableMongoEntity(Long id, Instant createdAt, Instant updatedAt) {
        super(id, createdAt); // Call superclass constructor
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createdAt", getCreatedAt())
                .append("updatedAt", updatedAt)
                .toString();
    }
}
