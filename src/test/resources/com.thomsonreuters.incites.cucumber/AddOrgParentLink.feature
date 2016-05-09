@Incites_API_Tests @Feature_AddOrgParentLink
Feature: Verifying the Add Organization Parent Link API

  #created by Nagasandeep.Banda@thomsonreuters.com on 22/03/2016

  @verify_Add_OrgParentLink_0001
  Scenario: Verification of AddOrgParentLink API response against Database
    Given I have verified the presence of parameters "OrgID;OrgIDParent" having values "300300308;17" from the database using the query "verify_Add_OrgParentLink_0001" and saved the result
    And I got the response from Incites API path "/AddOrgParentLink" using "post" request with input parameters "OrgID;OrgIDParent" having values as "300300308;17"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the results of the API performed operation from the database using the query "verify_Add_OrgParentLink_0001" for the passed parameters
    Then both the API and the database results should be matched