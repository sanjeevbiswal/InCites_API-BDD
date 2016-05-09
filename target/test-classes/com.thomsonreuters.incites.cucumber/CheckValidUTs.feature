@Incites_API_Tests @Feature_CheckValidUTs
Feature: Verifying the API UT Number Validation Functionality
  Checks whether the validity of a UT Number provided by the Server API is matching with Database validity or not

  #created by Nagasandeep.Banda@thomsonreuters.com on 19/02/2016
  # below scenarios will pass if both the api validation and database validation matches (eg: both says valid or invalid)

  @verify_Single_UT_Number_0001
  Scenario: Verification of a single UT Number against API and Database
    #additional parameters example : UT=000352040700014&RECORDS=20 -- params should be appended with '&' (and symbol)

    Given I have got the response from Incites API path "/CheckValidUTs/" in "json" format with additional parameters "UT=000352040700014"
    And I have verified the validity of the UT Number "000352040700014" using the database
    Then The server provided validity should match with the database provided validity

  @verify_Multiple_UT_Numbers_0001
  Scenario: Verification of multiple UT Numbers at a time against API and Database
    Given I have got the response from Incites API path "/CheckValidUTs/" in "json" format with an additional parameter "UT" with the below multiple values
    #below ones are the mix of both valid and invalid UT Numbers (Valid ones are 1st,3rd,5th)
      |000353267900023|
      |000111111111111|
      |000346982900018|
      |000123456789012|
      |000346342200004|
      |123456778909282|
    And I have verified the validity of the above mentioned UT Numbers using the database
    And I have extracted the validity of the above mentioned UT Numbers from the server api
    Then The server provided results should match with the results obtained from database