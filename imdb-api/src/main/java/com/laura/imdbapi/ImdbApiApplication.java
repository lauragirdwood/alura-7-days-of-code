package com.laura.imdbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class ImdbApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImdbApiApplication.class, args);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb-api.com/en/API/Top250Movies/<api-key>"))
                .build();

//        HttpResponse<String> response =
//                client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

    }

}
