package dev.kamva.movies.controller;

import dev.kamva.movies.model.Movie;
import dev.kamva.movies.model.Reviews;
import dev.kamva.movies.model.User;
import dev.kamva.movies.repository.UserRepository;
import dev.kamva.movies.service.ReviewService;
import dev.kamva.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/api/v1/reviews")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Reviews> createReview(@RequestBody Map<String, String> payload) {
        // Retrieve the movie from the database by IMDb ID
        Movie movie = movieService.findMovieByImdbId(payload.get("imdbId"));

        if (movie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if movie not found
        }

        // Get the logged-in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        // Create a new review using the review body, movie, and user
        Reviews newReview = reviewService.createReview(payload.get("reviewBody"), movie, user);

        // Return the created review
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }


}