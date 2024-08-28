package com.example.ExternalApi;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class LlamaChatService {

    private final ChatClient chatClient;

    public LlamaChatService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("Udzielaj odpowiedzi po polsku dotyczących pogody.")
                .build();
    }

    public String getAnswerForQuestion(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

}
