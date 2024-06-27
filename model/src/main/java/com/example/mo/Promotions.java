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
public class Promotions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer promotionID;

    @Column(nullable = false)
    private Integer cinemaOwnerID;

    @Column(nullable = false)
    private Integer movieID;

    @Column(length = 50, nullable = false, name = "promotionName")
    private String promotionName;

    @Column(length = 100, nullable = false, name = "description")
    private String description;

    @Column(name = "discountRate")
    private Double discountRate;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(nullable = true, length = 64, name = "photoPromotions")
    private String photoPromotions;

    @Transient
    public String getPhotosImagePath() {
        if (photoPromotions == null) return null;
        return "/promotions-photo/" + promotionID + "/" + photoPromotions;
    }
}
