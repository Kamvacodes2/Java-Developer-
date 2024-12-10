package dev.kamva.movies.service;

import dev.kamva.movies.model.Movie;
import dev.kamva.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Optional<Movie> singleMovie(String imdbId){
        return movieRepository.findMovieByImdbId(imdbId);
    }

    public Movie findMovieByImdbId(String imdbId) {
        Optional<Movie> movie = movieRepository.findMovieByImdbId(imdbId);
        return movie.orElse(null); // Return null if no movie found
    }
}
