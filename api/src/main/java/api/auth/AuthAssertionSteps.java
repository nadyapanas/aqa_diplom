package api.auth;

import io.qameta.allure.Step;

import static common.assertion.SoftAssertionsStorage.softAssert;

public class AuthAssertionSteps {

    @Step("Check token is not empty.")
    public void assertTokenIsNotEmpty(String token) {
        softAssert().assertThat(token)
                .as("Token is empty.")
                .isNotEmpty();
    }

    @Step("Check username already exists error message.")
    public void assertUsernameAlreadyExistsError(String errorMessage, String username) {
        softAssert().assertThat(errorMessage)
                .as("Wrong error message.")
                .isEqualTo("User with username '%s' already exists".formatted(username));
    }
}