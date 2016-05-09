@Incites_API_Tests @Feature_DeletePub
Feature: Verifying the Delete Publication API

  #created by Nagasandeep.Banda@thomsonreuters.com on 16/03/2016

  @verify_Delete_Publication_0001
  Scenario: Verification of DeletePub API response against Database
    Given I have verified the presence of "UT" having value "4165439255544" from the database using the query "verify_Delete_Publication_0001" and saved the result
    And I got the response from Incites API path "/DeletePub" using "post" request with input parameter "UT" having value as "4165439255544"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the result of the API performed operation from the database using the query "verify_Delete_Publication_0001"
    Then both the API and the database results should be matched