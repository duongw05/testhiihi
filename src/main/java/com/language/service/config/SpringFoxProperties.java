package com.language.service.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "springfox")
public class SpringFoxProperties {
    private String host = "127.0.0.1:8080";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
