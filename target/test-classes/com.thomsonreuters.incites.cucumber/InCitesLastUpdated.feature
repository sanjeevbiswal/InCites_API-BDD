@Incites_API_Tests @Feature_IncitesLastUpdated
Feature: Verification of Incites Last Updated Date and WOS Indexed Date provided by the Server API
  Compares the dates of last update to Web of Science and InCites datasets from the API with Database provided values

  #created by Nagasandeep.Banda@thomsonreuters.com on 18/02/2016
  #below scenario will pass if the dates provided by the  API matches with Database values


  @verify_Incites_LastUpdated_Date_0001
  Scenario: Verification of Incites Last Updated Date and WOS Indexed Date against API and Database
#    additional parameters example : UT=000352040700014&RECORDS=20 -- params should be appended with '&' (and symbol)

    Given I have got the response from Incites API path "/InCitesLastUpdated/" in "json" format with additional parameters "none"
    And I have extracted the dates "INCITES_DATASET_UPDATED" and "WOS_DATASET_INDEXED" from the response and the database
    Then API provided dates "INCITES_DATASET_UPDATED" and "WOS_DATASET_INDEXED" should match with the database provided date values