package com.laura.imdbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ImdbApiApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ImdbApiApplication.class, args);

        String apiKey = "k_w3pqxprh";
        URI apiIMDb = URI.create("https://imdb-api.com/en/API/Top250Movies/" + apiKey);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(apiIMDb).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        String[] moviesArray = parseJsonMovies(json);

        List<String> titles = parseTitles(moviesArray);
        titles.forEach(System.out::println);

        List<String> urlImages = parseUrlImages(moviesArray);
        urlImages.forEach(System.out::println);

        List<String> ratings = parseRatings(moviesArray);
        ratings.forEach(System.out::println);

        List<String> years = parseYears(moviesArray);
        years.forEach(System.out::println);

    }

    private static List<String> parseTitles(String[] moviesArray) {
        return parseAttribute(moviesArray, 2);
    }

    private static List<String> parseUrlImages(String[] moviesArray) {
        return parseAttribute(moviesArray, 5);
    }

    private static List<String> parseRatings(String[] moviesArray) {
        return parseAttribute(moviesArray, 7);
    }

    private static List<String> parseYears(String[] moviesArray) {
        return parseAttribute(moviesArray, 4);
    }

    private static String[] parseJsonMovies(String json) {
        Matcher matcher = Pattern.compile(".*\\[(.*)].*").matcher(json);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("no match in " + json);
        }

        String[] moviesArray = matcher.group(1).split("},\\{");
        moviesArray[0] = moviesArray[0].substring(1);
        int last = moviesArray.length - 1;
        String lastString = moviesArray[last];
        moviesArray[last] = lastString.substring(0, lastString.length() - 1);
        return moviesArray;
    }

    private static List<String> parseAttribute(String[] moviesArray, int pos) {
        return Stream.of(moviesArray)
                .map(e -> e.split("\",\"")[pos])
                .map(e -> e.split(":\"")[1])
                .map(e -> e.replaceAll("\"", ""))
                .collect(Collectors.toList());
    }

//    public static String[] parseJsonMovies(String jsonResponse) {
//        String jsonMovies = removeSquareBracketsFromOriginalJson(jsonResponse);
//        return jsonMovies.split("(^\\{)|(\\},\\{)|(\\}$)");
//    }

//    private static String removeSquareBracketsFromOriginalJson(String jsonResponse) {
//        return jsonResponse.substring(jsonResponse.indexOf('[') + 1, jsonResponse.indexOf(']'));
//    }
//
//    private static String[] extractAllMovieAttributes(String[] jsonMovies) {
//        return Arrays.toString(jsonMovies).split("(?<=\"),(?=\")|(?<=\"), (?=\")");
//    }

}
