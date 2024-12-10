package dev.kamva.movies.service;

import dev.kamva.movies.model.Movie;
import dev.kamva.movies.model.Reviews;
import dev.kamva.movies.model.User;
import dev.kamva.movies.repository.MovieRepository;
import dev.kamva.movies.repository.ReviewRepository;
import dev.kamva.movies.exceptionhandler.MovieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Reviews createReview(String reviewBody, Movie movie, User user) {
        Reviews review = new Reviews();
        review.setReviewBody(reviewBody); // Use the updated field name
        review.setMovie(movie); // Link the movie
        review.setUser(user); // Set the user

        // Link the review with the movie
        review.setMovie(movie);

        // Add the review to the movie's review list
        movie.getReviews().add(review);

        // Save the review separately
        reviewRepository.save(review);

        // Save the updated movie
        movieRepository.save(movie);

        // Return the review object
        return review;
    }
}
