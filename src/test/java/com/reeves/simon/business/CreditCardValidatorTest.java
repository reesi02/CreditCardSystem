package com.reeves.simon.business;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.reeves.simon.creditcardsystem.business.CreditCardValidator;
import com.reeves.simon.creditcardsystem.dto.CreditCardDTO;

public class CreditCardValidatorTest {
    private CreditCardDTO baseCard;

    // class under test
    private CreditCardValidator validator;

    @Before
    public void before() {
        baseCard = new CreditCardDTO();
        baseCard.setName("Alice");
        baseCard.setBalance(0L);
        baseCard.setCardLimit(10000L);
        baseCard.setCardNumber("4111111111111111");

        validator = new CreditCardValidator();
    }

    @Test
    public void cardNumberNull() {
        baseCard.setCardNumber(null);
        assertEquals( CreditCardValidator.ValidationResults.INVALID_CARD_NO, validator.validate(baseCard));
    }

    @Test
    public void cardNumberNotNumeric() {
        baseCard.setCardNumber("abcdabcdabcdabcd");
        assertEquals(CreditCardValidator.ValidationResults.INVALID_CARD_NO,	validator.validate(baseCard));
    }

    @Test
    public void cardNumberNumericWithDecimalPoint() {
        baseCard.setCardNumber("12341234123412.4");
        assertEquals(CreditCardValidator.ValidationResults.INVALID_CARD_NO,validator.validate(baseCard));
    }

    @Test
    public void cardNumberTooLong() {
        // more than 19 digits long
        // card number too long - 1 digit too long
        baseCard.setCardNumber("00000000000000000000");
        assertEquals(CreditCardValidator.ValidationResults.INVALID_CARD_NO,validator.validate(baseCard));
    }

    @Test
    public void cardNumberEmptyString() {
        baseCard.setCardNumber("");
        assertEquals(CreditCardValidator.ValidationResults.INVALID_CARD_NO,validator.validate(baseCard));
    }
    
    @Test
    public void cardNumberValid() {
        // some valid cards from https://www.easy400.net/js2/regexp/ccnums.html
        List<String> validCards = Arrays.asList(
                "4111111111111111", 	// Visa
                "5500000000000004", 	// MasterCard
                "340000000000009",		// American Express 
                "6011000000000004", 	// Discover
                "201400000000009", 		// en Route 
                "3088000000000009"		// JCB
                );

        validCards.forEach(card->{
            baseCard.setCardNumber(card);
            assertEquals(CreditCardValidator.ValidationResults.OK,validator.validate(baseCard));
        });

    }
    
    @Test
    public void cardNumberInvalid() {
        baseCard.setCardNumber("79927398714");
        assertEquals(CreditCardValidator.ValidationResults.INVALID_CARD_NO,validator.validate(baseCard));
    }
    
    @Test
    public void cardNameInvalid_Null() {
        baseCard.setName(null);
        assertEquals(CreditCardValidator.ValidationResults.INVALID_NAME, validator.validate(baseCard));
    }
    @Test
    public void cardNameInvalid_ZeroLength() {
        baseCard.setName("");
        assertEquals(CreditCardValidator.ValidationResults.INVALID_NAME, validator.validate(baseCard));
    }

    @Test
    public void cardNameInvalid_TooLong() {
        StringBuilder tooLong = new StringBuilder();
        for ( int i=0; i<201; i++) {
            tooLong.append('a');
        }
        baseCard.setName(tooLong.toString());
        assertEquals(CreditCardValidator.ValidationResults.INVALID_NAME, validator.validate(baseCard));
    }

    @Test
    public void cardNameValid_Long() {
        StringBuilder tooLong = new StringBuilder();
        for ( int i=0; i<200; i++) {
            tooLong.append('a');
        }
        baseCard.setName(tooLong.toString());
        assertEquals(CreditCardValidator.ValidationResults.OK, validator.validate(baseCard));
    }
    
    
    @Test
    public void cardLimitInvalid_LessThanZero() {
        baseCard.setCardLimit(-1);
        assertEquals(CreditCardValidator.ValidationResults.INVALID_LIMIT, validator.validate(baseCard));
    }
    
    @Test
    public void cardLimitValid_Zero() {
        baseCard.setCardLimit(0L);
        assertEquals(CreditCardValidator.ValidationResults.OK, validator.validate(baseCard));
    }

    @Test
    public void cardLimitValid_Positive() {
        baseCard.setCardLimit(1L);
        assertEquals(CreditCardValidator.ValidationResults.OK, validator.validate(baseCard));
    }

}
