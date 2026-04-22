package api.auth;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.stream.Stream;

public class AuthFaker {

    private final Faker faker = new Faker();

    public AuthRequest createAuthFakeRequest() {
        return AuthRequest.builder()
                .username(faker.name().username())
                .password(faker.internet().password(6, 14, true, true, true))
                .build();
    }

    public AuthRequest createAuthFakeRequest(String name) {
        return AuthRequest.builder()
                .username(name)
                .password(faker.internet().password(6, 14, true, true, true))
                .build();
    }

    public List<AuthRequest> createAuthFakeRequests(Integer count) {
        return Stream.generate(this::createAuthFakeRequest)
                .limit(count)
                .toList();
    }
}
