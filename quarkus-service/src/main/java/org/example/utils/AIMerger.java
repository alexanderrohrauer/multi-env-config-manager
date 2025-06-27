package org.example.utils;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AIMerger {
    @ConfigProperty(name = "org.example.mcfg.ai.model")
    String model;

    @Inject
    OpenAIClient client;

    public String merge(String base, String overlay, String ext) {
        var prompt = String.format("""
You are a system that merges two config files. Always prioritize the second config.

Always mind the following rules:
- Just output the merged config, without any side-notes and as plain-text. No markdown!
- There is a directive prefixed with "mcfg.io <instruction>" that gives you additional instructions on how you should merge the specific block. Remove the directive in the target.
- The "patch_<content>" directive tells you that just the content should be patched.

```%s
%s
```

```%s
%s
```
""", ext, base, ext, overlay);
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(prompt)
                .model(model)
                .build();
        var chatCompletion = client.chat().completions().create(params);
        return chatCompletion.choices().getFirst().message().content().orElseThrow();
    }
}
