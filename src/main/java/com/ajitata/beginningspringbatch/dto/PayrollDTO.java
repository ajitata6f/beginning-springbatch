package com.ajitata.beginningspringbatch.dto;

public class PayrollDTO {
    private String identification;
    private String currency;
    private double amount;
    private String accountType;
    private String accountNumber;
    private String description;
    private String firstName;
    private String lastName;

    public PayrollDTO() {
    }

    public PayrollDTO(String identification, String currency, double amount, String accountType, String accountNumber, String description, String firstName, String lastName) {
        this.identification = identification;
        this.currency = currency;
        this.amount = amount;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "PayrollDTO{" +
                "identification=" + identification +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", accountType='" + accountType + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", description='" + description + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
