package com.userservice.service;

import com.userservice.domain.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getMovies();
    Movie getMovie(Long id);
    Movie saveMovie(Movie movie);
    Movie updateMovie(Movie movie, long id);
    void deleteMovie(long id);
}
