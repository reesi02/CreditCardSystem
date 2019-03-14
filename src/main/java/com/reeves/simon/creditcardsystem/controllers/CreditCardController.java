package com.reeves.simon.creditcardsystem.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reeves.simon.creditcardsystem.business.CreditCardValidator;
import com.reeves.simon.creditcardsystem.dto.CreditCardDTO;
import com.reeves.simon.creditcardsystem.dto.ErrorResponse;
import com.reeves.simon.creditcardsystem.repo.CreditCardRepo;

@RestController
public class CreditCardController {

    @Autowired
    private CreditCardRepo creditCardRepo;
    
    @Autowired
    private CreditCardValidator creditCardValidator;

    /**
     * REST endpoint to creating an account with a POST request
     * Request body should contain a JSON representation of CreditCardDTO
     * 
     * @param card
     * @return ResponseEntity 201(CREATED) or 400(BAD REQUEST)
     */
    @RequestMapping(value="/api/v1/card",method=RequestMethod.POST)
    public ResponseEntity<?> addCreditCard(@RequestBody CreditCardDTO card) {
        CreditCardValidator.ValidationResults result = creditCardValidator.validate(card);
        if ( result != CreditCardValidator.ValidationResults.OK) {
            // 400 Bad request
            ErrorResponse errorBody = new ErrorResponse();
            // include details of the request failure in the error response body
            errorBody.setMsgCode(result.toString());
            ResponseEntity<ErrorResponse> response = new ResponseEntity<ErrorResponse>(errorBody,HttpStatus.BAD_REQUEST);
            return response;
        }

        // new cards are always assigned a balance of 0
        card.setBalance(0L);
        // save(or update) entity in the repository
        creditCardRepo.save(card);

        // CREATED 201 
        ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.CREATED);
        return response;

    }

    /**
     * REST endpoint to retrieve all the cards from the repository
     * 
     * @return List of all stored creditcards
     */
    @RequestMapping(value="/api/v1/card", method=RequestMethod.GET)
    public List<CreditCardDTO> getAllCards(){
        List<CreditCardDTO> allCards = new ArrayList<>();
        creditCardRepo.findAll().forEach(allCards::add);
        return allCards;
    }

}
