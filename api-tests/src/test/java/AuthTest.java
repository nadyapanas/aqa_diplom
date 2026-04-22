import org.testng.annotations.Test;
import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
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

@Test(groups = {"api", "auth"})
public class AuthTest {

    @AllureId("TC-001")
    @Description("Auth. Registration with valid credentials")
    public void testSignUp() {
        AuthSteps authSteps = new AuthSteps(new AuthService());
        AuthRequest authRequest = new AuthFaker().createAuthFakeRequest();

        AuthResponse authResponse = authSteps.register(authRequest);
        new AuthAssertionSteps().assertTokenIsNotEmpty(authResponse.getToken());

        UserDbSteps userDbSteps = new UserDbSteps(new UserDbService());
        UserEntity userEntity = userDbSteps.getByUsername(authRequest.getUsername());

        userDbSteps.deleteById(userEntity.getId());
    }

    @AllureId("TC-002")
    @Description("Auth. Registration with already exist username")
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
