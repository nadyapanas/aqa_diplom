import api.auth.AuthAssertionSteps;
import api.auth.AuthFaker;
import api.auth.AuthRequest;
import api.auth.AuthResponse;
import api.auth.AuthService;
import api.auth.AuthSteps;
import api.user.UserAssertionSteps;
import api.user.UserResponse;
import api.user.UserService;
import api.user.UserSteps;
import database.enums.Role;
import database.user.UserDbService;
import database.user.UserDbSteps;
import org.testng.annotations.Test;

import java.util.List;

@Test(groups = {"api", "user"})
public class UserTest {

    public void testListUsersWithAdminRole() {
        AuthSteps authSteps = new AuthSteps(new AuthService());
        AuthFaker authFaker = new AuthFaker();

        List<AuthRequest> authRequests = authFaker.createAuthFakeRequests(5);
        for (AuthRequest authRequest : authRequests) {
            authSteps.register(authRequest);
        }

        AuthRequest adminAuthRequest = authFaker.createAuthFakeRequest();
        AuthResponse adminAuthResponse = authSteps.register(adminAuthRequest);
        new AuthAssertionSteps().assertTokenIsNotEmpty(adminAuthResponse.getToken());
        UserDbSteps userDbSteps = new UserDbSteps(new UserDbService());
        Long adminUserId = userDbSteps.grantRole(adminAuthRequest.getUsername(), Role.ADMIN.toString());

        UserSteps userSteps = new UserSteps(new UserService());
        List<UserResponse> users = userSteps.getAllByAdmin(adminAuthResponse.getToken());
        new UserAssertionSteps().assertCollectionSize(authRequests.size() + 1, users.size());

        authRequests.forEach(authRq -> userDbSteps.deleteByUsername(authRq.getUsername()));
        userDbSteps.deleteById(adminUserId);
    }

    public void testListUsersWithUserRole() {
        AuthSteps authSteps = new AuthSteps(new AuthService());
        AuthFaker authFaker = new AuthFaker();

        List<AuthRequest> authRequests = authFaker.createAuthFakeRequests(5);
        for (AuthRequest authRequest : authRequests) {
            authSteps.register(authRequest);
        }

        AuthRequest userAuthRequest = authFaker.createAuthFakeRequest();
        AuthResponse userAuthResponse = authSteps.register(userAuthRequest);
        new AuthAssertionSteps().assertTokenIsNotEmpty(userAuthResponse.getToken());

        UserSteps userSteps = new UserSteps(new UserService());
        String response = userSteps.getAllByUser(userAuthResponse.getToken());
        new UserAssertionSteps().assertResponseIsEmptyString(response);

        UserDbSteps userDbSteps = new UserDbSteps(new UserDbService());
        authRequests.forEach(authRq -> userDbSteps.deleteByUsername(authRq.getUsername()));
        userDbSteps.deleteByUsername(userAuthRequest.getUsername());
    }
}
