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
}