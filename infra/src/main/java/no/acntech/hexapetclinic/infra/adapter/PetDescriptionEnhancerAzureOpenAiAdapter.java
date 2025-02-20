package no.acntech.hexapetclinic.infra.adapter;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestSystemMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.connector.PetDescriptionEnhancer;
import no.acntech.hexapetclinic.domain.model.PetType;
import no.acntech.hexapetclinic.utils.text.StringUtils;

@Slf4j
public class PetDescriptionEnhancerAzureOpenAiAdapter implements PetDescriptionEnhancer {

  private static final String OPENAI_API_KEY = "insert-your-azure-openai-api-key-here";
  private static final String OPENAI_ENDPOINT_URL = "https://someservice.openai.azure.com";
  private static final String OPENAI_DEPLOYMENT_OR_MODEL_NAME = "gpt-4o-mini";
  private static final int MAX_TOKENS = 150;
  private static final int MAX_RESPONSE_LENGTH = 500; // Max characters for safety

  private final OpenAIClient openAiClient;
  private final String deploymentOrModelName;

  public PetDescriptionEnhancerAzureOpenAiAdapter() {
    this(OPENAI_API_KEY, OPENAI_ENDPOINT_URL, OPENAI_DEPLOYMENT_OR_MODEL_NAME);
  }

  public PetDescriptionEnhancerAzureOpenAiAdapter(String azureOpenAiApiKey, String azureOpenAiEndpointUrl, String deploymentOrModelName) {
    this.openAiClient = new OpenAIClientBuilder()
        .credential(new com.azure.core.credential.KeyCredential(azureOpenAiApiKey))
        .endpoint(azureOpenAiEndpointUrl)
        .buildClient();
    this.deploymentOrModelName = deploymentOrModelName;
  }

  @Override
  public String enhanceDescription(@NonNull PetType petType, @NonNull String breed, @NonNull String description) {
    List<ChatRequestMessage> chatMessages = new ArrayList<>();

    // System message to set assistant behavior
    chatMessages.add(new ChatRequestSystemMessage(
        "You are an expert pet assistant. Provide concise, engaging, and fact-based enhancements. " +
            "Keep responses under " + MAX_RESPONSE_LENGTH + " characters."));

    // User message requesting enhancement
    String userMessage = String.format(
        "Enhance the following description with breed-specific details while keeping it concise and engaging. " +
            "Do not exceed %d characters.\n" +
            "Pet Type: %s\n" +
            "Breed: %s\n" +
            "Description: %s",
        MAX_RESPONSE_LENGTH, petType.name(), breed, description
    );
    chatMessages.add(new ChatRequestUserMessage(userMessage));

    try {
      // Call Azure OpenAI with token limit
      ChatCompletions chatCompletions = openAiClient.getChatCompletions(
          deploymentOrModelName,
          new ChatCompletionsOptions(chatMessages)
              .setMaxTokens(MAX_TOKENS) // Limit response tokens
      );

      StringBuilder enhancedDescription = new StringBuilder();
      for (ChatChoice choice : chatCompletions.getChoices()) {
        enhancedDescription.append(choice.getMessage().getContent());
      }

      String result = enhancedDescription.toString();

      // Ensure response does not exceed character limit
      result = StringUtils.truncate(result, MAX_RESPONSE_LENGTH);

//      log.debug("Generated enhanced description for pet {} of breed {}: {}", petType, breed, result);
      return result;
    } catch (RuntimeException e) {
      log.error("Error generating enhanced description using Azure OpenAI API: {}", e.getMessage(), e);
      return description;
    }
  }
}
