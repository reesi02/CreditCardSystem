package com.reeves.simon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reeves.simon.creditcardsystem.App;
import com.reeves.simon.creditcardsystem.dto.CreditCardDTO;
import com.reeves.simon.creditcardsystem.dto.ErrorResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=App.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
	
    private URI makeURI(String path) throws URISyntaxException {
        return new URI("http://localhost:"+port+path);
    }
    private URI api;

    private CreditCardDTO baseCard;

    @Before
    public void before() throws URISyntaxException {
        api = makeURI("/api/v1/card");
        baseCard = new CreditCardDTO();
        baseCard.setName("Alice");
        baseCard.setCardNumber("4111111111111111");
        baseCard.setCardLimit(1000L);
        baseCard.setBalance(0L);
    }

    @Test
    public void testAdd_NullBody() {
        // build a request
        RequestEntity<CreditCardDTO> request = RequestEntity.post(api)
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
        // make the request
        ResponseEntity<String> postResponse;
        postResponse = restTemplate.exchange( request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, postResponse.getStatusCode());
    }

    @Test
    public void testAdd_InvalidCardNumber() {
        baseCard.setCardNumber("0000111122223333");
        // build a request
        RequestEntity<CreditCardDTO> request = RequestEntity.post(api)
                .contentType(MediaType.APPLICATION_JSON)
                .body(baseCard);
        // make the request
        ResponseEntity<ErrorResponse> postResponse;
        postResponse = restTemplate.exchange( request, ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, postResponse.getStatusCode());
        assertEquals("INVALID_CARD_NO",postResponse.getBody().getMsgCode());
    }

    @Test
    public void testAdd_ValidCardAddGet() {
        // build a request
        RequestEntity<CreditCardDTO> request = RequestEntity.post(api)
                .contentType(MediaType.APPLICATION_JSON)
                .body(baseCard);

        // make the request
        ResponseEntity<String> postResponse;
        postResponse = restTemplate.exchange( request, String.class);
        // check the create response
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        
        // retrieve all cards and check for the new card
        RequestEntity<?> getAllCards = RequestEntity.get(api)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<List<CreditCardDTO>> getResponse;
        getResponse = restTemplate.exchange(getAllCards, new ParameterizedTypeReference<List<CreditCardDTO>>() {});

        // list should contain one card
        assertNotNull(getResponse);
        assertEquals(1,getResponse.getBody().size());
        // check it is the same card
        assertEquals(baseCard,getResponse.getBody().get(0));
    }
}
