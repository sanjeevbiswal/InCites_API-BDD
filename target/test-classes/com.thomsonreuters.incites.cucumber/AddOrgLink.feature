@Incites_API_Tests @Feature_AddOrgLink
Feature: Verifying the Add Organization Link API

  @verify_Add_OrgLink_0001
  Scenario: Verification of AddOrgLink API response against Database
    Given I have verified the presence of parameters "OrgID;CardID" having values "1;1212" from the database using the query "verify_Add_OrgLink_0001" and saved the result
    And I got the response from Incites API path "/AddOrgLink" using "post" request with input parameters "OrgID;CardID" having values as "1;1212"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the results of the API performed operation from the database using the query "verify_Add_OrgLink_0001" for the passed parameters
    Then both the API and the database results should be matched