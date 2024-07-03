package com.example.mo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieID;

    @Column(nullable = false)
    private Integer cinemaOwnerID;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "director", nullable = false)
    private String director;

    @Column(name = "cast", nullable = false)
    private String cast;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "releaseDate", nullable = false)
    private Date releaseDate;

    @Column(name = "languages", nullable = false)
    private String languages;

    @Column(name = "ratingCount")
    private Integer ratingCount = 0;

    @Column(name = "averageRating")
    private Double averageRating = 0.0;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "trailerURL", nullable = false)
    private String trailerURL;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "status_movie", nullable = false)
    private String statusMovie;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Showtime> showtimes;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    // Getter và Setter cho address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public enum StatusMovie {
        NOW_SHOWING("Đang Chiếu"),
        COMING_SOON("Sắp Chiếu");

        private final String status;

        StatusMovie(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public void updateDetails(Movie movieDetails) {
        this.title = movieDetails.getTitle();
        this.genre = movieDetails.getGenre();
        this.duration = movieDetails.getDuration();
        this.director = movieDetails.getDirector();
        this.cast = movieDetails.getCast();
        this.releaseDate = movieDetails.getReleaseDate();
        this.languages = movieDetails.getLanguages();
        this.ratingCount = movieDetails.getRatingCount();
        this.averageRating = movieDetails.getAverageRating();
        this.description = movieDetails.getDescription();
        this.trailerURL = movieDetails.getTrailerURL();
        this.address = movieDetails.getAddress();  // Cập nhật thuộc tính này
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieID=" + movieID +
                ", cinemaOwnerID=" + cinemaOwnerID +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", director='" + director + '\'' +
                ", cast='" + cast + '\'' +
                ", releaseDate=" + releaseDate +
                ", languages='" + languages + '\'' +
                ", ratingCount=" + ratingCount +
                ", averageRating=" + averageRating +
                ", description='" + description + '\'' +
                ", trailerURL='" + trailerURL + '\'' +
                ", address='" + address + '\'' +
                ", statusMovie='" + statusMovie + '\'' +
                '}';
    }
}
