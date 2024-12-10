package dev.kamva.movies.controller;

import dev.kamva.movies.model.Movie;
import dev.kamva.movies.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Operation(summary = "Get all movies",
            description = "Fetch a list of all movies.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movies retrieved successfully")
            })
    @GetMapping
    public ResponseEntity<List<Movie>> getMovies() {
        return new ResponseEntity<List<Movie>>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @Operation(summary = "Get movie by IMDb ID",
            description = "Fetch a single movie by its IMDb ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movie retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Movie not found")
            })
    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(
            @Parameter(description = "IMDb ID of the movie to fetch", required = true)
            @PathVariable String imdbId) {

        Optional<Movie> movie = movieService.singleMovie(imdbId);
        return movie.isPresent() ?
                new ResponseEntity<>(movie, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
}
