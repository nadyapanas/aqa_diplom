package api.auth;

import api.base.ApiService;
import api.base.Request;

public class AuthService extends ApiService {

    public static final String SIGH_UP = "/auth/sign-up";
    public static final String SIGH_IN = "/auth/sign-in";

    public static final String AUTH_SCHEMA_PATH = "schemas/auth/auth_token_schema.json";

    public AuthResponse signIn(AuthRequest authRequest) {
        return post(Request.builder()
                .url(API_BASE_URL)
                .path(SIGH_IN)
                .body(authRequest)
                .headers(getDefaultHeaders())
                .responseClass(AuthResponse.class)
                .schemaName(AUTH_SCHEMA_PATH)
                .build()
        );
    }

    public AuthResponse signUp(AuthRequest authRequest) {
        return post(Request.builder()
                .url(API_BASE_URL)
                .path(SIGH_UP)
                .body(authRequest)
                .headers(getDefaultHeaders())
                .responseClass(AuthResponse.class)
                .schemaName(AUTH_SCHEMA_PATH)
                .build()
        );
    }
}
