package api.base;

import java.util.Map;

import io.restassured.http.Method;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private String url;
    private String path;
    private Map<String, String> params;
    private Map<String, String> headers;
    private Object body;
    private String schemaName;
    private Class<?> responseClass;
    private Method method;
    private Integer responseStatusCode;
}


