package api.user;

import io.qameta.allure.Step;

import static common.assertion.SoftAssertionsStorage.softAssert;

public class UserAssertionSteps {

    @Step("Check user collection size")
    public UserAssertionSteps assertCollectionSize(int expectedSize,
                                                   int actualSize) {
        softAssert().assertThat(expectedSize)
                .isGreaterThanOrEqualTo(actualSize);
        return this;
    }

    @Step("Check response is empty string")
    public UserAssertionSteps assertResponseIsEmptyString(String response) {
        softAssert().assertThat(response).isEmpty();
        return this;
    }
}
