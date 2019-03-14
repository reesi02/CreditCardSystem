# Credit Card System
--------------------

Programming exersize for full stack developer position.

Implements two REST apis for adding and retrieving creditcard details
# Add Credit Card: #
## POST http://host:8080/api/v1/card ##
Request Body is a JSON representation of a Customer/Card with this format:

  {"name":"Robert","cardNumber":"000000","cardLimit":134500}

The card number is validated with LUHN10. On successful entity creation the API will return HttpStatus code 200(OK).
If the request fails because of an invalid request, the response code will be 400 (Bad Request). The body of this response will include one of the following reasons:
 * INVALID_OBJECT,
 * INVALID_CARD_NO,
 * INVALID_NAME,
 * INVALID_LIMIT
  
# Get All Credit Cards #
## GET http://host:8080/api/v1/card ##
This api will return an array of credit card records. The objects in the response have the following format:
{   "name":"robert",
    "cardNumber":"000000",
    "cardLimit":100000,
    "balance":0
}

# Financial Records #
The financial records are transmitted and stored in pence, so to obtain the value in pounds, division by 100 is necessary.

# Automated Testing #
Unit and Integration testing of the REST API have been provided and can be found here:
## Card Validator Tests ##

`src/test/java/com/reeves/simon/business/CreditCardValidatorTest.java`
Contains unit test cases to verify the behaviour of the card validation routine.

##  Integration Test ##

`/src/test/java/com/reeves/simon/IntegrationTest.java`
The Integration test starts the REST service on a random port, and then makes REST requests to the sevice to create an entity. The test then gets all the cards to verify the card details were created successfully.

## Running ##
After cloning the source, you can build the jar file form the root directory (which contains pom.mxl) with

`mvn comile`

You can run the test with

`mvn test`

You can create the JAR file with

`mvn package`

You can start the service with

`java -jar target/CreditCardSystem-1.0.0-SNAPSHOT.jar`

## Web UI ##
When the service is running the Web UI is accessible from http://host:8080/. 
The Web UI has been implemented with AngularJS and Bootstrap.
