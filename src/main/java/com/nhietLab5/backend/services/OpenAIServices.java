package com.nhietLab5.backend.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OpenAIServices {
    //Dung bien moi truong de luu tru API_KEY
    @Value("${OPENAI_API_KEY1}")
    private String API_KEY;
    private final String API_URL = "https://api.openai.com/v1/completions";

    public String getResponse(String prompt) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("prompt", prompt);
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 100);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(API_KEY);

            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.postForObject(API_URL, entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
