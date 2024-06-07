package com.example.mo;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer NewsID;
    private Integer AuthorID;
    private Integer MovieID;
    private Integer CinemaOwnerID;

    @Column(length = 50, nullable = false, name = "Title")
    private String Title;
    @Column(length = 100, nullable = false, name = "Content")
    private String Content;
    @Column(name = "PublishDate")
    private Date PublishDate;
    @Column(nullable = true, length = 64, name = "PhotosImagePath")
    private String PhotoNews;

    public Integer getNewsID() {
        return this.NewsID;
    }

    public void setNewsID(Integer NewsID) {
        this.NewsID = NewsID;
    }

    public Integer getAuthorID() {
        return this.AuthorID;
    }

    public void setAuthorID(Integer AuthorID) {
        this.AuthorID = AuthorID;
    }

    @Transient
    public String getPhotosImagePath() {
        if (PhotoNews == null) return null;

        return "/news-photo/" + NewsID + "/" + PhotoNews;
    }


    public String getPhotoNews() {
        return this.PhotoNews;
    }
    

    public void setPhotoNews(String PhotoNews) {
        this.PhotoNews = PhotoNews;
    }

    public Integer getMovieID() {
        return this.MovieID;
    }

    public void setMovieID(Integer MovieID) {
        this.MovieID = MovieID;
    }

    public Integer getCinemaOwnerID() {
        return this.CinemaOwnerID;
    }

    public void setCinemaOwnerID(Integer CinemaOwnerID) {
        this.CinemaOwnerID = CinemaOwnerID;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContent() {
        return this.Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public Date getPublishDate() {
        return this.PublishDate;
    }

    public void setPublishDate(Date PublishDate) {
        this.PublishDate = PublishDate;
    }


    @Override
    public String toString() {
        return "{" +
            " NewsID='" + getNewsID() + "'" +
            ", AuthorID='" + getAuthorID() + "'" +
            ", MovieID='" + getMovieID() + "'" +
            ", CinemaOwnerID='" + getCinemaOwnerID() + "'" +
            ", Title='" + getTitle() + "'" +
            ", Content='" + getContent() + "'" +
            ", PublishDate='" + getPublishDate() + "'" +
            "}";
    }

}