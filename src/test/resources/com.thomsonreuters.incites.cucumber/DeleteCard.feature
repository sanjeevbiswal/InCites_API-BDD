@Incites_API_Tests @Feature_DeleteCard
Feature: Verifying the Delete Card API

  #created by Nagasandeep.Banda@thomsonreuters.com on 16/03/2016

  @verify_Delete_Card_0001
  Scenario: Verification of DeletePub API response against Database
    Given I have verified the presence of "CardID" having value "1111" from the database using the query "verify_Delete_Card_0001" and saved the result
    And I got the response from Incites API path "/DeleteCard" using "post" request with input parameter "CardID" having value as "1111"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the result of the API performed operation from the database using the query "verify_Delete_Card_0001"
    Then both the API and the database results should be matched