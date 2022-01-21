Feature: GET Books details from ToolsQA API
  Book store swagger url : https://bookstore.toolsqa.com/swagger/index.html

  Scenario: Create User in Bookstore API
    Given User navigate to bookstore API
    When post api request with username and password details
    When send request to generate autorized token
    Then send authorized token details to api request
    Then delete the created user
