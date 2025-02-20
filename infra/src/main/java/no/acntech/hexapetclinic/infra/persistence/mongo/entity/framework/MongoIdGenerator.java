package no.acntech.hexapetclinic.infra.persistence.mongo.entity.framework;

import java.util.UUID;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class MongoIdGenerator {

    /**
     * Generates a unique Long ID from a UUID.
     */
    public static Long generateId() {
        UUID uuid = UUID.randomUUID();
        return Math.abs(uuid.getMostSignificantBits()); // Ensure positive Long
    }

    /**
     * Converts an existing UUID to a Long ID.
     */
    public static Long fromUUID(UUID uuid) {
        return Math.abs(uuid.getMostSignificantBits());
    }

    /**
     * Converts a Long back to a UUID (for debugging).
     */
    public static UUID toUUID(Long id) {
        return new UUID(id, 0L);
    }
}
