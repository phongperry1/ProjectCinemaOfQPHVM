package com.example.mo;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(name = "authorID")
    private Integer AuthorID;

    @ManyToOne
    @JoinColumn(name = "movieID")
    private Movie MovieID;

    @ManyToOne
    @JoinColumn(name = "cinemaOwnerID") // Tên cột khóa ngoại trong bảng Food
    private CinemaOwner cinemaOwner;

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
        if (PhotoNews == null)
            return null;

        return "/news-photo/" + NewsID + "/" + PhotoNews;
    }

    public String getPhotoNews() {
        return this.PhotoNews;
    }

    public void setPhotoNews(String PhotoNews) {
        this.PhotoNews = PhotoNews;
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

}