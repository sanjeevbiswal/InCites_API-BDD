@Incites_API_Tests @Feature_DeleteOrgLink
Feature: Verifying the Delete Oraganization Link API

  #created by Nagasandeep.Banda@thomsonreuters.com on 16/03/2016

  @verify_Delete_OrgLink_0001
  Scenario: Verification of DeleteOrgLink API response against Database
    Given I have verified the presence of parameters "OrgID;CardID" having values "300300317;111" from the database using the query "verify_Delete_OrgLink_0001" and saved the result
    And I got the response from Incites API path "/DeleteOrgLink" using "post" request with input parameters "OrgID;CardID" having values as "300300317;111"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the results of the API performed operation from the database using the query "verify_Delete_OrgLink_0001" for the passed parameters
    Then both the API and the database results should be matched