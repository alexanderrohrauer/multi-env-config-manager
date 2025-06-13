package org.example;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URL;
import java.util.Optional;

@Singleton
public class OpenAIClientProducer {
    @ConfigProperty(name = "org.example.mcfg.ai.api_key")
    String apiKey;

    @ConfigProperty(name = "org.example.mcfg.ai.base_url")
    Optional<String> baseUrl;

    @Produces
    public OpenAIClient produceOpenAIClient() {
        var builder = OpenAIOkHttpClient.builder().apiKey(apiKey);
        baseUrl.ifPresent(builder::baseUrl);
        return builder.build();
    }
}
