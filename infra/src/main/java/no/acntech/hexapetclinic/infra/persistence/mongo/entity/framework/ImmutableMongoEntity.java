package no.acntech.hexapetclinic.infra.persistence.mongo.entity.framework;

/**
 * Superclass for Immutable MongoDB entities - does not include an 'updatedAt' attribute.
 */
public abstract class ImmutableMongoEntity extends MongoEntity {
}
