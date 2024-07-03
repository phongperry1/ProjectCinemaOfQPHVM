
package com.example.mo;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String userName;
    private String email;
    private String phone;
    private String userPassword;
    private String role;
    private boolean status;
    private String verificationCode;
    private Date birthdate;
    private String location;
    private String gender;
    private String userRank;
    private Integer memberPoints = 0; 
    private String paymentMethod;
    private String userType;
    private String profileImageURL;
    private String resetPasswordToken;
    private Integer cinemaOwnerID; 
    private Double virtualWallet = 0.0; 

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Transaction> transactions;

    @OneToOne(mappedBy = "users")
    private CinemaOwner cinemaOwner;

    // Getters and setters

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isStatus() {
        return this.status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getVerificationCode() {
        return this.verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserRank() {
        return this.userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    public Integer getMemberPoints() {
        return this.memberPoints;
    }

    public void setMemberPoints(Integer memberPoints) {
        this.memberPoints = memberPoints;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProfileImageURL() {
        return this.profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getResetPasswordToken() {
        return this.resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public Integer getCinemaOwnerID() {
        return this.cinemaOwnerID;
    }

    public void setCinemaOwnerID(Integer cinemaOwnerID) {
        this.cinemaOwnerID = cinemaOwnerID;
    }

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public CinemaOwner getCinemaOwner() {
        return cinemaOwner;
    }

    public void setCinemaOwner(CinemaOwner cinemaOwner) {
        this.cinemaOwner = cinemaOwner;
    }

    // Methods to deposit and withdraw money
    public void deposit(Double amount) {
        if (amount > 0) {
            this.virtualWallet += amount;
        } else {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
    }

    public void withdraw(Double amount) {
        if (amount > 0 && this.virtualWallet >= amount) {
            this.virtualWallet -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient balance or invalid amount.");
        }
    }

    public void addMemberPoints(int points) {
        this.memberPoints += points;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", role='" + role + '\'' +
                ", status=" + status +
                ", verificationCode='" + verificationCode + '\'' +
                ", birthdate=" + birthdate +
                ", location='" + location + '\'' +
                ", gender='" + gender + '\'' +
                ", userRank='" + userRank + '\'' +
                ", memberPoints=" + memberPoints +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", userType='" + userType + '\'' +
                ", profileImageURL='" + profileImageURL + '\'' +
                ", resetPasswordToken='" + resetPasswordToken + '\'' +
                ", cinemaOwnerID=" + cinemaOwnerID +
                ", virtualWallet=" + virtualWallet +
                '}';
    }
}
