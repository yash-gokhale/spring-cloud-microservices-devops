package org.example.common.dto;

public class AccountResponse {
    private String customerId;
    private boolean hasSufficientBalance;
    private String accountStatus;
    private double finalPrice;

    public  AccountResponse() {}

    public AccountResponse(String customerId, boolean hasSufficientBalance, String accountStatus) {
        this.customerId = customerId;
        this.hasSufficientBalance = hasSufficientBalance;
        this.accountStatus = accountStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isHasSufficientBalance() {
        return hasSufficientBalance;
    }

    public void setHasSufficientBalance(boolean hasSufficientBalance) {
        this.hasSufficientBalance = hasSufficientBalance;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}

