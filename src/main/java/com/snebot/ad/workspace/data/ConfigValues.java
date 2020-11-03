package com.snebot.ad.workspace.data;

import org.apache.commons.lang3.StringUtils;

public class ConfigValues {
    private String user;
    private String password;
    private String server;
    private String port;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public ConfigValues() {}

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().isAssignableFrom(this.getClass())) {
            ConfigValues configValues = (ConfigValues)obj;
            return StringUtils.equals(configValues.getPassword(), this.getPassword()) &&
                    StringUtils.equals(configValues.getUser(), this.getUser()) &&
                    StringUtils.equals(configValues.getServer(), this.getServer()) &&
                    StringUtils.equals(configValues.getPort(), this.getPort());
        }

        return super.equals(obj);
    }
}
