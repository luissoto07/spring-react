package com.userservice.service;

import com.userservice.domain.Movie;
import com.userservice.repo.MovieRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepo movieRepo;

    @Override
    public List<Movie> getMovies() {
        log.info("Fetching all movies ");
        return movieRepo.findAll();
    }

    @Override
    public Movie getMovie(Long id) {
        log.info("Fetching movie {}", id);
        return movieRepo.findById(id).orElseThrow(()->new IllegalStateException("Movie not found"));
    }

    @Override
    public Movie saveMovie(Movie movie) {
        log.info("Saving new movie {} into the db", movie.getTitle());
        return movieRepo.save(movie);
    }

    @Override
    public Movie updateMovie(Movie movie, long id) {
        log.info("Updating movie {} into the db", id);
        return movieRepo.findById(id).map(myMovie -> {
            myMovie.setTitle(movie.getTitle());
            myMovie.setLength(movie.getLength());
            myMovie.setCreator(movie.getCreator());

            return movieRepo.save(myMovie);
        }).orElseThrow(() -> new IllegalStateException("Movie not found"));
    }

    @Override
    public void deleteMovie(long id) {
        Movie movie = movieRepo.findById(id).orElseThrow(() -> new IllegalStateException("Movie not found"));
        log.info("Deleting movie {} of the db", movie.getTitle());
        movieRepo.delete(movie);
    }
}
