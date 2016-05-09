@Incites_API_Tests @Feature_DeleteAuthorLink
Feature: Verifying the Delete Author Link API

  #created by Nagasandeep.Banda@thomsonreuters.com on 16/03/2016

  @verify_Delete_AuthorLink_0001
  Scenario: Verification of DeleteAuthorLink API response against Database
    Given I have verified the presence of parameters "AuthorID;CardID" having values "1234;1111" from the database using the query "verify_Delete_AuthorLink_0001" and saved the result
    And I got the response from Incites API path "/DeleteAuthorLink" using "post" request with input parameters "AuthorID;CardID" having values as "1234;1111"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the results of the API performed operation from the database using the query "verify_Delete_AuthorLink_0001" for the passed parameters
    Then both the API and the database results should be matched