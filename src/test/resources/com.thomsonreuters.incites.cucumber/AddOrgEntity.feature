@Incites_API_Tests @Feature_AddOrgEntity
Feature: Verifying the ADD Organization Entity API

  #created by Nagasandeep.Banda@thomsonreuters.com on 17/03/2016

  @verify_Add_OrgEntity_0001
  Scenario: Verification of AddOrgEntity API response against Database
#    Given I have verified the presence of below given parameters for all the mentioned rows from the database using the query "verify_Add_OrgEntity_0001" and saved the results
    Given I have verified the presence of database rows for all the below mentioned entities from the database using the query "check_presence_of_ORGID" and saved the results
      |OrgID;OrgName;OrgType;WID;OrgIDParent|
      |1348976;USPOLO;TV;1120;8                |
      |8723456;LEVIS;Jeans;1199;18           |
      |4542340;Central;Training;1178;17           |
    And I got the response from Incites API path "/AddOrgEntity" using "post" request with above mentioned input parameters having values as mentioned above
    And I have extracted the API response and validated it against database using the query"verify_Add_OrgEntity_0001" and stored the results
    Then the API response results should be matched with database results