package api.user;

import java.util.Arrays;
import java.util.List;

import api.base.ApiService;
import api.base.Request;
import io.restassured.response.ValidatableResponse;

public class UserService extends ApiService {
    public static final String LIST_USERS = "/users";
    public static final String CREATE_USER = "/users";
    public static final String GET_USER = "/users/%s";

    public static final String LIST_USERS_SCHEMA_PATH = "schemas/user/get_users_schema.json";
    public static final String CREATE_USER_SCHEMA_PATH = "schemas/user/create_user_schema.json";
    public static final String PUT_USERS_SCHEMA_PATH = "schemas/user/put_users_schema.json";

    public List<UserResponse> getUsers(String token) {
        return Arrays.asList(get(Request.builder()
                .url(API_BASE_URL)
                .path(LIST_USERS)
                .headers(getDefaultHeaders(token))
                .responseClass(UserResponse[].class)
                .schemaName(LIST_USERS_SCHEMA_PATH)
                .build()
        ));
    }

    public ValidatableResponse getUsersForbidden(String token) {
        return get(Request.builder()
                .url(API_BASE_URL)
                .path(LIST_USERS)
                .headers(getDefaultHeaders(token))
                .responseStatusCode(403)
                .build()
        );
    }

    public UserResponse postUsers(User user, String token) {
        return post(Request.builder()
                .url(API_BASE_URL)
                .path(CREATE_USER)
                .body(user)
                .headers(getDefaultHeaders(token))
                .responseClass(UserResponse.class)
                .schemaName(CREATE_USER_SCHEMA_PATH)
                .build()
        );
    }

    public UserResponse putUsers(User user, String token) {
        return put(Request.builder()
                .url(API_BASE_URL)
                .path(GET_USER.formatted(user.getId()))
                .body(user)
                .headers(getDefaultHeaders(token))
                .responseClass(UserResponse.class)
                .schemaName(PUT_USERS_SCHEMA_PATH)
                .build()
        );
    }
}
