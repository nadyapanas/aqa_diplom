package api.user;

import io.qameta.allure.Step;

import java.util.List;

public class UserSteps {
    private final UserService userService;

    public UserSteps(UserService userService) {
        this.userService = userService;
    }

    @Step("Get users list")
    public List<UserResponse> getAllByAdmin(String token) {
        return userService.getUsers(token);
    }

    @Step("Get users list")
    public String getAllByUser(String token) {
        return userService.getUsersForbidden(token).extract().asString();
    }
}
