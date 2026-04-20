package database.user;

import io.qameta.allure.Step;

import static common.assertion.SoftAssertionsStorage.softAssert;

public class UserDbAssertionSteps {
    private final UserEntity userEntity;

    public UserDbAssertionSteps(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Step("Проверяем, что имя пользователя в БД равно '{username}'")
    public UserDbAssertionSteps assertUserName(String username) {
        softAssert().assertThat(userEntity.getUsername())
                .as("Имя пользователя в БД не соответствует ожидаемому")
                .isEqualTo(username);
        return this;
    }

    @Step("Проверяем, что имя пользователя в БД равно '{username}'")
    public UserDbAssertionSteps assertUserWithNameExist(String username) {
        softAssert().assertThat(userEntity.getUsername())
                .as("Имя пользователя в БД не соответствует ожидаемому")
                .isEqualTo(username);
        return this;
    }
}

