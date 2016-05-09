@Incites_API_Tests @Feature_DeleteAuthor
Feature: Verifying the Delete Author API

  #created by Nagasandeep.Banda@thomsonreuters.com on 16/03/2016

  @verify_Delete_Author_0001
  Scenario: Verification of DeleteAuthor API response against Database
    Given I have verified the presence of "AuthorID" having value "31208408" from the database using the query "verify_Delete_Author_0001" and saved the result
    And I got the response from Incites API path "/DeleteAuthor" using "post" request with input parameter "AuthorID" having value as "31208408"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the result of the API performed operation from the database using the query "verify_Delete_Author_0001"
    Then both the API and the database results should be matched