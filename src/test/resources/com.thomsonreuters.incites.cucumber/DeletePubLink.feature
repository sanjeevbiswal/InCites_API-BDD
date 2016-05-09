@Incites_API_Tests @Feature_DeletePubLink
Feature: Verifying the Delete Publication Link API

  #created by Nagasandeep.Banda@thomsonreuters.com on 16/03/2016

  @verify_Delete_PubLink_0001
  Scenario: Verification of DeletePubLink API response against Database
    Given I have verified the presence of parameters "UT;CardID" having values "0002;2" from the database using the query "verify_Delete_PubLink_0001" and saved the result
    And I got the response from Incites API path "/DeletePubLink" using "post" request with input parameters "UT;CardID" having values as "0002;2"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the results of the API performed operation from the database using the query "verify_Delete_PubLink_0001" for the passed parameters
    Then both the API and the database results should be matched