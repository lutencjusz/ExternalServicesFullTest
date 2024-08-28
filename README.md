
# ExternalServicesFullTest

## Overview

This project demonstrates the integration of external weather services in a Spring Boot application, with comprehensive testing coverage. The application fetches weather data from an external API and provides endpoints to access this data. Additionally, the project includes unit and integration tests to ensure the reliability and correctness of the application's functionality.

## Technologies

The project utilizes the following technologies:

- **Spring Boot**: A framework for building Java-based web applications and microservices.
- **Spring Web**: Used for building RESTful web services.
- **Spring Cache**: Caching mechanism to improve performance by storing frequently accessed data.
- **Mockito**: A popular mocking framework used for unit testing.
- **JUnit 5**: Testing framework for Java applications.
- **Spring Test**: Integration testing support for Spring applications.
- **Dotenv**: Library for loading environment variables from a `.env` file.
- **llama 3**: External API based on the llama 3 API used for fetching weather descriptions.

## Project Structure

- `ExternalApiController`: Controller responsible for handling HTTP requests and interacting with the external API service.
- `WeatherGuiController`: Controller responsible HTTP GUI based on Thymeleaf.'
- `ExternalApiService`: Service class that communicates with the external weather API to fetch data.
- `WeatherDto`: Data Transfer Object (DTO) representing the weather data.
- `OpenWeatherDto`: DTO representing the structure of the response from the OpenWeather API.
- `Test Classes`: Unit and integration tests to ensure the functionality of the application.
- `LlamaChatService`: Service class that communicates with the llama 3 API to fetch weather descriptions.

## Endpoints

- **`GET /external`**: Fetches and returns the weather data from the external API.
- **`GET /chat`**: Integrates with a chat service (e.g., using a language model) to provide weather-related responses.

## Testing

The project includes extensive testing using JUnit and Mockito:

- **Unit Tests**: Test the individual components and services, ensuring they behave as expected.
- **Integration Tests**: Verify the integration between components and the interaction with external APIs.
- **Mocking**: External API calls are mocked using Mockito to ensure that tests are isolated and do not depend on real API calls.

## How to Run

1. **Clone the repository**:
   ```sh
   git clone https://github.com/lutencjusz/ExternalServicesFullTest.git
   ```
   
2. **Navigate to the project directory**:
   ```sh
   cd ExternalServicesFullTest
   ```

3. **Set up environment variables**:
   - Create a `.env` file in the root directory with the necessary API keys, e.g.:
     ```
     API_KEY=your_openweather_api_key
     ```

4. **Run the application**:
   ```sh
   ./mvnw spring-boot:run
   ```

5. **Run the tests**:
   ```sh
   ./mvnw test
   ```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
