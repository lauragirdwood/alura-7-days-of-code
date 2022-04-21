package com.laura.imdbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.PrintWriter;
import java.util.List;

@SpringBootApplication
public class ImdbApiApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ImdbApiApplication.class, args);

        String apiKey = "<sua-chave>";

        String json = new ImdbApiClient(apiKey).getBody();

        List<Movie> movies = new ImdbMovieJsonParser(json).parse();

        PrintWriter writer = new PrintWriter("content.html");
        new HTMLGenerator(writer).generate(movies);
        writer.close();

    }


}
