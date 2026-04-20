package database.user;

import io.qameta.allure.Step;

public class UserDbSteps {
    private final UserDbService UserDbService;

    public UserDbSteps(UserDbService UserDbService) {
        this.UserDbService = UserDbService;
    }

    @Step("Get user from 'users' table: 'id' = '{id}'")
    public UserEntity getById(Long id) {
        return UserDbService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User with id = " + id + " not found"));
    }

    @Step("Get user from 'users' table: 'username' = '{username}'")
    public UserEntity getByUsername(String username) {
        return UserDbService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username = " + username + " not found"));
    }

    @Step("Delete user from 'users' table: 'id' = '{id}' ")
    public boolean deleteById(Long id) {
        return UserDbService.deleteUserById(id);
    }

    @Step("Delete user from 'users' table: 'username' = '{username}' ")
    public boolean deleteByUsername(String username) {
        return UserDbService.deleteUserByUsername(username);
    }

    @Step("Grant '{role}' role for user: 'username' = '{username}' ")
    public Long grantRole(String username, String role) {
        return UserDbService.grantUserRole(username, role);
    }
}
