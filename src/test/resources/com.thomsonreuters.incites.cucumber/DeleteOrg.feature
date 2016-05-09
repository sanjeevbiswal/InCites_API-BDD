@Incites_API_Tests @Feature_DeleteOrg
Feature: Verifying the Delete Organization API

  #created by Nagasandeep.Banda@thomsonreuters.com on 16/03/2016

  @verify_Delete_Organization_0001
  Scenario: Verification of DeleteOrg API response against Database
    Given I have verified the presence of "OrgID" having value "300300308" from the database using the query "verify_Delete_Organization_0001" and saved the result
    And I got the response from Incites API path "/DeleteOrg" using "post" request with input parameter "OrgID" having value as "300300308"
    And I have extracted the result of the API performed operation from the API provided response
    And I have extracted the result of the API performed operation from the database using the query "verify_Delete_Organization_0001"
    Then both the API and the database results should be matched