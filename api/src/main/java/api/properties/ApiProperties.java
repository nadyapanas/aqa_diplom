package api.properties;

import common.properties.ConfProperties;

public class ApiProperties extends ConfProperties {

    public ApiProperties() {
        loadProperties("api.properties");
    }

    public String getApiBaseUrl() {
        return getRequired("api.base.url");
    }
}
