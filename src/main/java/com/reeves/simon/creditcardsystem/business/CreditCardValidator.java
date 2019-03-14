package com.reeves.simon.creditcardsystem.business;

import org.springframework.stereotype.Component;

import com.reeves.simon.creditcardsystem.dto.CreditCardDTO;

@Component
public class CreditCardValidator {

    public enum ValidationResults{
        OK,
        INVALID_OBJECT,
        INVALID_CARD_NO,
        INVALID_NAME,
        INVALID_LIMIT
}

    public ValidationResults validate(CreditCardDTO card) {

        if ( card == null) {
            return ValidationResults.INVALID_OBJECT;
        }
        
        if ( !validateCardNumber(card)) {
            return ValidationResults.INVALID_CARD_NO;
        }
        
        // check for a name
        if (( card.getName()==null ) ||
            ( card.getName().length()==0 ) ||
            ( card.getName().length()>200 ))
        {
            return ValidationResults.INVALID_NAME;
        }
        
        // card limit should be positive or zero
        if (card.getCardLimit() < 0 ) {
            return ValidationResults.INVALID_LIMIT;
        }

        return ValidationResults.OK;
    }

    /***
     * Check card string for null, length, numeric and LUHN checking
     * @param card
     * @return boolean indicating valid(true) and invalid(false)
     */
    private boolean validateCardNumber(CreditCardDTO card) {
        final String cardNumber = card.getCardNumber();
        // reject on null card
        if ( cardNumber == null) {
            return false;
        }
        
        // reject cards which are too long more than 19 characters
        int cardLength = cardNumber.length();		if ( cardLength>19){
            return false;
        }

        // check all digits at numeric
        try{
            Long.parseLong(cardNumber);
        }catch(NumberFormatException nfe) {
            return false;
        }
        
        // LUHN
        int sum = 0;
        // step 1 - starting at the right double the value of every second digit
        for ( int i=0; i<cardLength;  i++) {
            // ascii digit to digit value
            int digit = cardNumber.charAt((cardLength-i)-1)-'0';
            if ( i%2!=0) {
                // step 2 - if doubling the digit results in a two digit number 
                // add the digits together to get a single digit
                int doubleDigit = digit*2;
                if ( doubleDigit>9) {
                    // add the two digits
                    int rhd = (doubleDigit%10);
                    int lhd = (doubleDigit-rhd)/10;
                    doubleDigit = lhd+rhd;
                }
                // step 4 maintain a sum of the digits
                digit = doubleDigit;
            }
            // step 3 - maintain a sum of all the digits
            sum+=digit;
        }
        
        // step 4 - if the modulo 10 of sum is equal to 0 (ends in 0) then the number is valid
        return sum % 10 == 0;
    }
}
