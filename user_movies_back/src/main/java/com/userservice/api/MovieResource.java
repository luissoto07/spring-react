package com.userservice.api;

import com.userservice.domain.Movie;
import com.userservice.repo.MovieRepo;
import com.userservice.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class MovieResource {

    private final MovieService movieService;
    private final MovieRepo movieRepo;
    @CrossOrigin()
    @GetMapping("/get_all")
    public ResponseEntity<List<Movie>> getUsers() {
        return ResponseEntity.ok().body(movieService.getMovies());
    }
    @CrossOrigin()
    @GetMapping("/get/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable(name="id") Long id){
        if(!movieRepo.existsById(id)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(movieService.getMovie(id));
    }
    @CrossOrigin()
    @PutMapping("/update/{id}")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie, @PathVariable(name="id") Long id){
        return ResponseEntity.ok().body(movieService.updateMovie(movie, id));
    }
    @CrossOrigin()
    @PostMapping("/save")
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie){
        return ResponseEntity.ok().body(movieService.saveMovie(movie));
    }
    @CrossOrigin()
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable(name="id") Long id){
        if(!movieRepo.existsById(id)){
            return ResponseEntity.badRequest().build();
        }
        movieRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
