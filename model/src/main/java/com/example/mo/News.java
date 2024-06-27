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
    private Integer newsID;

    @Column(nullable = false)
    private Integer authorID;

    @Column(nullable = false)
    private Integer movieID;

    @Column(nullable = false)
    private Integer cinemaOwnerID;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String content;

    @Column
    private Date publishDate;

    @Column(nullable = true, length = 64)
    private String photoNews;

    // Getters and setters

    @Transient
    public String getPhotosImagePath() {
        if (photoNews == null) return null;
        return "/news-photo/" + newsID + "/" + photoNews;
    }
}
