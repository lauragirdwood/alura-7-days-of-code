package com.laura.imdbapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class Movie {

    private String title;
    private String urlImage;
    private String rating;
    private String year;

}
