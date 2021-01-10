package com.sreyas.skill.challenge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds all the values as configured in the property file
 * Author : Sreyas V Pariyath
 * Date   : 07/12/20
 * Time   : 07:12 PM
 */
@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private final List<Users> users = new ArrayList<>();
    private String portalUrl;

    public static class Users {
        private String username;
        private String password;
        private List<String> roles;

        public String getUsername() {
            return username;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

        public String getPassword() {
            return password;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<String> getRoles() {
            return roles;
        }
    }

    public List<Users> getUsers() {
        return users;
    }

    public String getPortalUrl() {
        return portalUrl;
    }

    public void setPortalUrl(String portalUrl) {
        this.portalUrl = portalUrl;
    }
}