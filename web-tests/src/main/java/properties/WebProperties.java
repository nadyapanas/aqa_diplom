package properties;

import common.properties.ConfProperties;

public class WebProperties extends ConfProperties {

    public WebProperties() {
        loadProperties("web.properties");
    }

    public String getWebUserEmail() {
        return getRequired("web.user.email");
    }

    public String getWebUserPassword() {
        return getRequired("web.user.password");
    }

    public String getWebUserFirstName() {
        return getRequired("web.user.first.name");
    }

    public String getWebUserSecondName() {
        return getRequired("web.user.second.name");
    }
}
