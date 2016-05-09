@Incites_API_Tests @Feature_AddAuthorLink
Feature: Verifying the Add Author Link API

  #created by Nagasandeep.Banda@thomsonreuters.com on 22/03/2016

  @verify_Add_AuthorLink_0001
  Scenario: Verification of AddAuthorLink API response against Database
    Given I have verified the presence of parameters "CardID;AuthorID" having values "789634;1234" from the database using the query "verify_Add_AuthorLink_0001" and saved the result
    And I got the response from Incites API path "/AddAuthorLink" using "post" request with input parameters "CardID;AuthorID" having values as "789634;1234"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the results of the API performed operation from the database using the query "verify_Add_AuthorLink_0001" for the passed parameters
    Then both the API and the database results should be matched