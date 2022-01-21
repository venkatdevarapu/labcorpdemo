Feature: To Validate if the user is able to search and apply for careers


  Scenario: Validate Careers functionality
    Given I navigate to labcorp website
    Then the lapcorp site must appear with career link
    When I click on the Careers tab
    Then the Careers page must open in a new tab
    When I search for Careers in the career page
    Then the search results matching the search criteria must appear
    When I click on the matched Career
    Then the results page must match with the search criteria
    When I click on the Apply Now button
    Then the results must match with the previous page

