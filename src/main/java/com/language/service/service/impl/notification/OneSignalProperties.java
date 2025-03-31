package com.language.service.service.impl.notification;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "one-signal")
public class OneSignalProperties {
    private String appId = "b9f77ed5-dada-40b8-9554-cee07922e537";
    private String apiKey = "MTM2NWM0NWUtMWQ3NS00MWQ4LWFhMjMtYTI2YTc2NDY1Y2Y4";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
