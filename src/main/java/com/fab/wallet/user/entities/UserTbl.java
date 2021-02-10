package com.fab.wallet.user.entities;

import com.fab.wallet.transaction.entities.TransactionTbl;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USER_TBL")
public class UserTbl {
    @Id
    @Column(name = "USER_ID",length = 60)
    String userId;
    @Column(name = "PASSWORD",length=40)
    String password;
    @Column(name = "FIRST_NAME",length=40)
    String firstName;
    @Column(name = "LAST_NAME",length=40)
    String lastName;
    @Column(name = "PHONE",length=15)
    String phone;
    @Column(name = "DOB")
    LocalDate DOB;
    @Column(name = "EXPIRY_DATE")
    LocalDate expiryDate;
    @Column(name = "CREATION_DATE")
    LocalDateTime creationDateTime;
    @Column(name = "LAST_UPDATED_DATE")
    LocalDateTime lastUpdatedDateTime;
    @Column(name = "ADDRESS",length=120)
    String address;
    @Column(name = "CITY",length=40)
    String city;
    @Column(name = "STATE",length=40)
    String state;
    @Column(name = "COUNTRY",length=40)
    String country;
    @Column(name = "WALLET_BALANCE")
    int walletBalance=0;

    @OneToMany(mappedBy = "toUser",fetch = FetchType.LAZY)
    List<TransactionTbl> creditTransactions;

    @OneToMany(mappedBy = "fromUser",fetch = FetchType.LAZY)
    List<TransactionTbl> debitTransactions;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(int walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(LocalDateTime lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<TransactionTbl> getCreditTransactions() {
        return creditTransactions;
    }

    public void setCreditTransactions(List<TransactionTbl> creditTransactions) {
        this.creditTransactions = creditTransactions;
    }

    public List<TransactionTbl> getDebitTransactions() {
        return debitTransactions;
    }

    public void setDebitTransactions(List<TransactionTbl> debitTransactions) {
        this.debitTransactions = debitTransactions;
    }
}
