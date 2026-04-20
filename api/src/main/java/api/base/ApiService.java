package api.base;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import api.properties.ApiProperties;

import static io.restassured.RestAssured.given;

public class ApiService {
    protected static final String API_BASE_URL = new ApiProperties().getApiBaseUrl();
    protected static final Integer SUCCESS_RESPONSE_STATUS = 200;

    protected <T> T get(Request request) {
        return execute(Method.GET, request);
    }

    protected <T> T post(Request request) {
        return execute(Method.POST, request);
    }

    protected <T> T put(Request request) {
        return execute(Method.PUT, request);
    }

    protected <T> T patch(Request request) {
        return execute(Method.PATCH, request);
    }

    protected <T> T delete(Request request) {
        return execute(Method.DELETE, request);
    }

    private RequestSpecification getRequestSpecification(
            String url,
            Map<String, String> params,
            Map<String, String> headers,
            Object body
    ) {
        RequestSpecification requestSpecification = given()
                .log()
                .all()
                .baseUri(url)
                .filters(List.of(new AllureRestAssured()))
                .queryParams(Optional.ofNullable(params).orElse(Collections.emptyMap()))
                .headers(Optional.ofNullable(headers).orElse(Collections.emptyMap()));
        return specifyBody(requestSpecification, body);
    }

    private RequestSpecification specifyBody(RequestSpecification spec,
                                             Object body) {
        if (Objects.isNull(body)) {
            return spec.body(StringUtils.EMPTY);
        }
        return switch (body) {
            case byte[] bytes -> spec.body(bytes);
            case String str -> spec.body(str);
            case File file -> spec.body(file);
            case InputStream is -> spec.body(is);
            default -> spec.body(body);
        };
    }

    @SuppressWarnings("unchecked")
    private <T> T execute(Method method,
                          Request request) {
        ValidatableResponse validatableResponse = getValidatableResponse(method, request);
        Class<?> responseClass = request.getResponseClass();
        if (Objects.isNull(responseClass) || responseClass == ValidatableResponse.class) {
            return (T) validatableResponse;
        }
        return (T) extractResponse(validatableResponse, request.getSchemaName(), responseClass);
    }

    protected <T> T extractResponse(ValidatableResponse validatableResponse,
                                    String schemaName,
                                    Class<T> responseClass) {
        if (Objects.nonNull(schemaName)) {
            validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaName));
        }
        return validatableResponse.extract()
                .as(responseClass);
    }

    protected ValidatableResponse getValidatableResponse(Method method,
                                                         Request request) {
        RequestSpecification requestSpecification =
                getRequestSpecification(request.getUrl(), request.getParams(), request.getHeaders(),
                        request.getBody());
        String path = getPath(request.getPath());
        ValidatableResponse validatableResponse = switch (method) {
            case Method.GET -> requestSpecification.get(path).then();
            case Method.POST -> requestSpecification.post(path).then();
            case Method.PUT -> requestSpecification.put(path).then();
            case Method.PATCH -> requestSpecification.patch(path).then();
            case Method.DELETE -> requestSpecification.delete(path).then();
            default -> null;
        };
        assert validatableResponse != null;
        validatableResponse.log().all();
        Integer statusCode = Objects.nonNull(request.getResponseStatusCode())
                ? request.getResponseStatusCode()
                : SUCCESS_RESPONSE_STATUS;
        validatableResponse.statusCode(statusCode);
        return validatableResponse;
    }

    private String getPath(String path) {
        return Objects.requireNonNullElse(path, StringUtils.EMPTY);
    }

    protected Map<String, String> getDefaultHeaders() {
        return Map.of("content-type", "application/json");
    }

    protected Map<String, String> getDefaultHeaders(String token) {
        return Map.of("content-type", "application/json", "Authorization", "Bearer %s".formatted(token));
    }
}