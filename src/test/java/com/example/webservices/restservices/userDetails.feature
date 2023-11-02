Feature: Testing Various Scenarios on reqres APi

    Scenario: Verify if the user's name is "Janet"
    Given url 'https://reqres.in/api/users'
    When method GET
    Then status 200
    And match response.data[1].first_name == 'Janet'

  Scenario: Get User by ID
    Given url 'https://reqres.in/api/users'
    Given path '/3'
    When method GET
    Then status 200
    And match response.data.last_name == 'Wong'

    Scenario: Check First Name in Response with Query Parameter
      Given url 'https://reqres.in/api/users'
      And param page = 2
      When method GET
      Then status 200
      And match response.data[1].first_name == 'Lindsay'

  Scenario: Check POST Request and Response
    Given url 'https://reqres.in/api/users'
    And request { name: 'Sam', job: 'Project Leader' }
    When method POST
    Then status 201
    And match response.job contains 'Leader'

  Scenario: Check POST Request and Response
    Given url 'https://reqres.in/api/users/2'
    And request { name: 'Adam', job: 'Software Engineer' }
    When method PUT
    Then status 200
    And match response.job contains 'Software'

  Scenario: Check PATCHRequest and Response
    Given url 'https://reqres.in/api/users/2'
    And request { name: 'Isha' }
    And header Content-Type = 'application/json'

    When method PATCH
    Then status 200
    And match response.name == 'Isha'

  Scenario: Check DELETE Request
    Given url 'https://reqres.in/api/users/2'
    When method DELETE
    Then status 204

  Scenario: Get User by ID Using PathParam
    Given url 'https://reqres.in/api/users'
    Given path '2'
    When method GET
    Then status 404

  Scenario: Validate POST request to /register endpoint with basic authentication

    Given url 'https://reqres.in/api'
    And path '/register'
    And header Authorization = 'Basic c3lkbmV5QGZpZmU='
    And request { "email": "sydney@fife" }
    When method POST
    Then status 200
