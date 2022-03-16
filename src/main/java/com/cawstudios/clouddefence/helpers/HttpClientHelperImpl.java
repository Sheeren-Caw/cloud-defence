package com.cawstudios.clouddefence.helpers;

import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SuppressWarnings("unused")
@Singleton
public class HttpClientHelperImpl implements HttpClientHelper {

    private static final String APPLICATION_JSON = "application/json";

    @Override
    public HttpResponse<String> post(String url, Object body, String token) throws IOException, InterruptedException {
        String bodyStr = ObjectHelper.objectToString(body);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", token)
                .header("Content-Type", APPLICATION_JSON)
                .header("accept", APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(bodyStr))
                .build();

        HttpResponse<String> response;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    @Override
    public HttpResponse<String> get(String url, String token) throws IOException, InterruptedException, IllegalArgumentException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", token)
                .header("Content-Type", APPLICATION_JSON)
                .header("accept", APPLICATION_JSON)
                .GET()
                .build();

        HttpResponse<String> response;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
