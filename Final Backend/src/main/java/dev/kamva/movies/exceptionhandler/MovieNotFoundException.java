package dev.kamva.movies.exceptionhandler;

public class MovieNotFoundException extends RuntimeException {
  public MovieNotFoundException(String imdbId) {

    super("Movie with imdbId " + imdbId + " not found");
  }
}
