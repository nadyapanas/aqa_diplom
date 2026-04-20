package database.properties;

import common.properties.ConfProperties;

public class DbProperties extends ConfProperties {

    public DbProperties() {
        loadProperties("db.properties");
    }

    public String getDbHost() {
        return getRequired("db.host");
    }

    public String getDbPort() {
        return getRequired("db.port");
    }

    public String getDbName() {
        return getRequired("db.name");
    }

    public String getDbUser() {
        return getRequired("db.user");
    }

    public String getDbPassword() {
        return getRequired("db.password");
    }
}
