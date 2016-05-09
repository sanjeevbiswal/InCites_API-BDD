@Incites_API_Tests @Feature_DeleteOrgParentLink
Feature: Verifying the Delete Oraganization Parent Link API

  #created by Nagasandeep.Banda@thomsonreuters.com on 17/03/2016

  @verify_Delete_OrgParentLink_0001
  Scenario: Verification of DeleteOrgParentLink API response against Database
    Given I have verified the presence of parameters "OrgID;OrgIDParent" having values "8;5" from the database using the query "verify_Delete_OrgParentLink_0001" and saved the result
    And I got the response from Incites API path "/DeleteOrgParentLink" using "post" request with input parameters "OrgID;OrgIDParent" having values as "8;5"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the results of the API performed operation from the database using the query "verify_Delete_OrgParentLink_0001" for the passed parameters
    Then both the API and the database results should be matched