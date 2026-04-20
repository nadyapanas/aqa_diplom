import api.auth.AuthAssertionSteps;
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
}
