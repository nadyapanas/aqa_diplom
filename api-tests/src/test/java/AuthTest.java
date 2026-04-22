import api.auth.AuthAssertionSteps;
import api.auth.AuthErrorResponse;
import api.auth.AuthFaker;
import api.auth.AuthService;
import api.auth.AuthSteps;
import api.auth.AuthResponse;
import api.auth.AuthRequest;
import database.user.UserDbService;
import database.user.UserDbSteps;
import database.user.UserEntity;
import org.testng.annotations.Test;

@Test(groups = {"api", "auth"})
public class AuthTest {

    public void testSignUp() {
        AuthSteps authSteps = new AuthSteps(new AuthService());
        AuthRequest authRequest = new AuthFaker().createAuthFakeRequest();

        AuthResponse authResponse = authSteps.register(authRequest);
        new AuthAssertionSteps().assertTokenIsNotEmpty(authResponse.getToken());

        UserDbSteps userDbSteps = new UserDbSteps(new UserDbService());
        UserEntity userEntity = userDbSteps.getByUsername(authRequest.getUsername());

        userDbSteps.deleteById(userEntity.getId());
    }

    public void testSignUpWithNotUniqueUsername() {
        AuthSteps authSteps = new AuthSteps(new AuthService());
        AuthFaker faker = new AuthFaker();

        AuthRequest authRequest = faker.createAuthFakeRequest();
        authSteps.register(authRequest);

        AuthRequest nonUniqueUsernameAuthRequest = new AuthFaker().createAuthFakeRequest(authRequest.getUsername());
        AuthErrorResponse response = authSteps.registerBadRequest(nonUniqueUsernameAuthRequest);
        new AuthAssertionSteps().assertUsernameAlreadyExistsError(response.getError(), authRequest.getUsername());

        UserDbSteps userDbSteps = new UserDbSteps(new UserDbService());
        userDbSteps.deleteByUsername(authRequest.getUsername());
    }
}
