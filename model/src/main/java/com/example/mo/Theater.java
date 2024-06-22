package com.example.mo;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
<<<<<<< HEAD
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
=======
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer TheaterID;

    private Integer CinemaOwnerID;

    @Column(length = 45 ,nullable = false, name = "theaterName" )
    private String TheaterName;
    
    @Column(length = 100, nullable = false, name = "address")
    private String Address;

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "showtimeID")
    private Showtime showtime;

=======
>>>>>>> 6c7489f4898546a3617d29820026795e5c34ba36

    public Integer getTheaterID() {
        return this.TheaterID;
    }

    public void setTheaterID(Integer TheaterID) {
        this.TheaterID = TheaterID;
    }

    public Integer getCinemaOwnerID() {
        return this.CinemaOwnerID;
    }

    public void setCinemaOwnerID(Integer CinemaOwnerID) {
        this.CinemaOwnerID = CinemaOwnerID;
    }

    public String getTheaterName() {
        return this.TheaterName;
    }

    public void setTheaterName(String TheaterName) {
        this.TheaterName = TheaterName;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }



    @Override
    public String toString() {
        return "{" +
            " TheaterID='" + getTheaterID() + "'" +
            ", CinemaOwnerID='" + getCinemaOwnerID() + "'" +
            ", TheaterName='" + getTheaterName() + "'" +
            ", Address='" + getAddress() + "'" +
            "}";
    }

}