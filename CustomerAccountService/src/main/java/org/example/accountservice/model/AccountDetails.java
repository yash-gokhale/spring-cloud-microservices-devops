package org.example.accountservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.example.common.dto.AccountType;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_details")
public class AccountDetails {

    @Id
    private String customerId;

    private String customerName;

    private String customerEmail;

    private double walletBalance;

    private String accountStatus;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private LocalDateTime accountCreationDate;

    public AccountDetails(){}

    public AccountDetails(String customerName, String customerEmail, double walletBalance, double totalPrice, String accountStatus, LocalDateTime accountCreationDate, LocalDateTime recentTransactionDate) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.walletBalance = walletBalance;
        this.accountStatus = accountStatus;
        this.accountCreationDate = accountCreationDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public LocalDateTime getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(LocalDateTime accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

}


