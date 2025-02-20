package no.acntech.hexapetclinic.infra.config.persistence;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter.EmailAddressReadConverter;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter.EmailAddressWriteConverter;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter.InstantReadConverter;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter.InstantWriteConverter;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter.LocalDateReadConverter;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter.LocalDateWriteConverter;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter.PetIdentifierReadConverter;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter.PetIdentifierWriteConverter;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter.TelephoneNumberReadConverter;
import no.acntech.hexapetclinic.infra.persistence.mongo.entity.converter.TelephoneNumberWriteConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions.MongoConverterConfigurationAdapter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "no.acntech.hexapetclinic.infra.persistence.mongo.repository")
@EnableMongoAuditing
@Slf4j
public class MongoConfig extends AbstractMongoClientConfiguration {

  @Value("${spring.data.mongodb.database}")
  private String databaseName;

  @Value("${spring.data.mongodb.uri}")
  private String mongoUri;

  @Override
  protected String getDatabaseName() {
    return databaseName;
  }

  @Bean
  public MongoOperations mongoOperations(MongoTemplate mongoTemplate) {  // Define MongoOperations explicitly
    return mongoTemplate;
  }

  @Override
  @Bean
  public MongoClient mongoClient() {
    log.info("Creating MongoDB client for URI: {}", mongoUri);
    return MongoClients.create(mongoUri);
  }


  @Bean  // Ensure mongoTemplate is defined FIRST
  public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory, MappingMongoConverter mappingMongoConverter) {
    log.info("Creating MongoTemplate");
    return new MongoTemplate(mongoDatabaseFactory, mappingMongoConverter);
  }

//  @Bean
//  public MappingMongoConverter mappingMongoConverter(
//      MongoDatabaseFactory mongoDatabaseFactory,
//      MongoCustomConversions mongoCustomConversions,
//      MongoMappingContext context) {
//    log.info("Creating MappingMongoConverter");
//    DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
//    MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, context);
//    converter.setCustomConversions(mongoCustomConversions);
//    converter.afterPropertiesSet();
//    return converter;
//  }
//
//  @Primary
//  @Bean
//  public MongoCustomConversions mongoCustomConversions() {
//    log.info("Creating custom Mongo conversions");
//    return new MongoCustomConversions(List.of(
//        new InstantReadConverter(),
//        new InstantWriteConverter(),
//        new LocalDateReadConverter(),
//        new LocalDateWriteConverter(),
//        new PetIdentifierReadConverter(),
//        new PetIdentifierWriteConverter(),
//        new TelephoneNumberReadConverter(),
//        new TelephoneNumberWriteConverter(),
//        new EmailAddressReadConverter(),
//        new EmailAddressWriteConverter()
//    ));
//  }

  @Override
  protected void configureConverters(MongoConverterConfigurationAdapter adapter) {
    log.info("Registering custom Mongo conversions");

    adapter.registerConverter(new InstantReadConverter());
    adapter.registerConverter(new InstantWriteConverter());
    adapter.registerConverter(new LocalDateReadConverter());
    adapter.registerConverter(new LocalDateWriteConverter());
    adapter.registerConverter(new PetIdentifierReadConverter());
    adapter.registerConverter(new PetIdentifierWriteConverter());
    adapter.registerConverter(new TelephoneNumberReadConverter());
    adapter.registerConverter(new TelephoneNumberWriteConverter());
    adapter.registerConverter(new EmailAddressReadConverter());
    adapter.registerConverter(new EmailAddressWriteConverter());
  }

}