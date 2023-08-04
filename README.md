# travel-app-backend

# Requirements

To execute this project, you need the following:
- Java 8 or higher
- Maven

# Clone the Project
To get started, clone this repository to your local machine using the following command:

# Execution

With a terminal, navigate to the project's root directory and run the following command:


# After Running

Once the application is up and running, you can access the exposed endpoints and interact with the project.

# Endpoints

The following endpoints are available for use:

- Endpoint 1: http://localhost:8080/api/v1/city/search?q={city}
- Endpoint 2: localhost:8080/api/v1/city/detail/?city={city}&countryCode={countryCode}&countryName={countryName}

## How to Consume Endpoints

Use the HTTP GET method to consume the exposed endpoints. You can use tools like cURL, Postman, or your preferred web browser to interact with the application.

Exposed Api:
  api to search city:
    -GET http://localhost:8080/api/v1/city/search?q={city}
