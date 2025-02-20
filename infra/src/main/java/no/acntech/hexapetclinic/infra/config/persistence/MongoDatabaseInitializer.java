package no.acntech.hexapetclinic.infra.config.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Initializes MongoDB with data from `data.json`.
 * <p>
 * Ensures the database is **completely cleared** before inserting fresh test data.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MongoDatabaseInitializer implements CommandLineRunner {

  private final MongoClient mongoClient;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Value("${application.datasource.name}")
  private String dbName;

  @Override
  public void run(String... args) {
    log.info("🛠 Initializing MongoDB with test data...");

    // Load data.json from classpath
    ClassPathResource resource = new ClassPathResource("data.json");

    if (!resource.exists()) {
      log.warn("⚠ data.json not found in classpath! Skipping MongoDB initialization.");
      return;
    }

    try (InputStream inputStream = resource.getInputStream()) {
      List<Map<String, Object>> collections = objectMapper.readValue(inputStream, new TypeReference<>() {});

      MongoDatabase database = mongoClient.getDatabase(dbName);

      // **Drop the entire database to ensure a clean state**
      database.drop();
      log.info("🔥 Dropped database '{}'. Fresh data will be inserted.", dbName);

      for (Map<String, Object> collectionData : collections) {
        String collectionName = (String) collectionData.get("collection");
        List<Map<String, Object>> documents = (List<Map<String, Object>>) collectionData.get("documents");

        if (collectionName == null || documents == null) {
          log.warn("⚠ Skipping invalid collection data in data.json");
          continue;
        }

        MongoCollection<Document> collection = database.getCollection(collectionName);
        log.info("⬆ Inserting {} documents into collection '{}'", documents.size(), collectionName);

        List<Document> mongoDocuments = documents.stream()
            .map(Document::new)
            .toList();

        if (!mongoDocuments.isEmpty()) {
          collection.insertMany(mongoDocuments);
          log.info("Successfully inserted {} documents into '{}'", mongoDocuments.size(), collectionName);
        }
      }

    } catch (IOException e) {
      log.error("Failed to load data.json: {}", e.getMessage(), e);
    }
  }
}
