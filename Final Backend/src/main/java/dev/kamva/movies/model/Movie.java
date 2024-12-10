package dev.kamva.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("imdbId")
    @Column(name = "imdb_id", nullable = false, unique = true)
    private String imdbId;

    @Column(name = "title")
    private String title;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "trailer_link")
    private String trailerLink;

    @Column(name = "poster")
    private String poster;

    @ElementCollection
    @Column(name = "genres")
    private List<String> genres;

    @ElementCollection
    @Column(name = "backdrops")
    private List<String> backdrops;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // Prevent infinite recursion during serialization
    @JsonIgnore
    private List<Reviews> reviews = new ArrayList<>();

    public List<Reviews> getReviews() {
        return reviews;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}
