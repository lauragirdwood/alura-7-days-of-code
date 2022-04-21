package com.laura.imdbapi;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Movie {

    private String title;
    private String urlImage;
    private String rating;
    private String year;

}
