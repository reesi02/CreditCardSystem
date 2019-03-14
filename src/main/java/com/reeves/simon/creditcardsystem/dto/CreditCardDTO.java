package com.reeves.simon.creditcardsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class CreditCardDTO {

    @Id
    @Column(
        name="CardNumber",
        length=19,
        nullable=false)
    @Size(max=19)
    private String cardNumber;

    @Column(
        name="CustomerName",
        length=200,
        nullable=false)
    @Size(  max=200)
    private String name;

    // value in pence, divide by 100 to get value in pounds
    @Column(
        name="Balance",
        nullable=false)
    private long balance;
    
    // value in pence, divide by 100 to get value in pounds
    @Column(
        name="CardLimit",
        nullable=false)
    private long cardLimit;
    
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public long getBalance() {
        return balance;
    }
    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getCardLimit() {
        return cardLimit;
    }
    
    public void setCardLimit(long cardLimit) {
        this.cardLimit = cardLimit;
    }

    public boolean equals(Object other) {
        if ( other instanceof CreditCardDTO){
            CreditCardDTO ccOther = (CreditCardDTO)other;
            // check name
            if ( !ccOther.getName().equals(this.name)) {
                return false;
            }
            // check card number
            if ( !ccOther.getCardNumber().equals(cardNumber)){
                return false;
            }
            // check balance
            if ( ccOther.getBalance()!=this.balance) {
               return false;
            }

            // check card limit
            if ( ccOther.getCardLimit() != this.cardLimit) {
                return false;
            }
        }else {
            // wrong type
            return false;
        }
        return true;
    }
}
