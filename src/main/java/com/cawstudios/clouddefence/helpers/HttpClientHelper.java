package com.cawstudios.clouddefence.helpers;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface HttpClientHelper {
    HttpResponse<String> post(String url, Object body, String token) throws IOException, InterruptedException;

    HttpResponse<String> get(String url, String token) throws IOException, InterruptedException, IllegalArgumentException;
}
