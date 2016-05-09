@Incites_API_Tests @Feature_AddPubLink
Feature: Verifying the Add  Pub Link API


  @verify_add_PubLink_0001
  Scenario: Verification of AddPubLink API response against Database
    Given I have verified the presence of parameters "UT;CardID" having values "8956898989;1111" from the database using the query "verify_Add_pub_Link_0001" and saved the result
    And I got the response from Incites API path "/AddPubLink" using "post" request with input parameters "UT;CardID" having values as "8956898989;1111"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the results of the API performed operation from the database using the query "verify_Add_pub_Link_0001" for the passed parameters
    Then both the API and the database results should be matched