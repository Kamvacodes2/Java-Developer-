package dev.kamva.movies.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.kamva.movies.model.Movie;
import dev.kamva.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
public class MovieSeeder implements CommandLineRunner {

    @Autowired
    private MovieRepository movieRepository;

    @Value("classpath:data.json")  // Load data.json from resources
    private Resource resource;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Load JSON data from resource
            InputStream inputStream = resource.getInputStream();

            // Parse JSON data using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            List<Movie> movies = objectMapper.readValue(inputStream, new TypeReference<List<Movie>>() {});

            // Save movies to database if they do not exist already
            for (Movie movie : movies) {
                // Check if the movie with the same imdbId already exists
                Optional<Movie> existingMovie = movieRepository.findMovieByImdbId(movie.getImdbId());

                // If the movie doesn't exist, save it to the database
                if (existingMovie.isEmpty()) {
                    movieRepository.save(movie);
                } else {
                    System.out.println("Movie with imdbId " + movie.getImdbId() + " already exists.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace to understand the error
        }
    }
}


