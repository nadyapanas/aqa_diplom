package api.auth;

import io.qameta.allure.Step;


public class AuthSteps {

    private final AuthService authService;

    public AuthSteps(AuthService authService) {
        this.authService = authService;
    }

    @Step("Register user 'username' = '{AuthRequest.username}'")
    public AuthResponse register(AuthRequest authRequest) {
        return authService.signUp(authRequest);
    }

    @Step("Login user 'username' = '{AuthRequest.username}'")
    public AuthResponse login(AuthRequest authRequest) {
        return authService.signIn(authRequest);
    }
}
