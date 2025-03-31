package com.language.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {
    private long accessTokenLifeTimeMinutes = 5;
    private long refreshTokenLifeTimeMinutes = 60;

    /**
     * Hệ thống có scan và gửi notification hay không.
     * Đối với đa số thời gian ở môi trường local thì giá trị
     * hợp lí là false (không cần gửi notification)
     */
    private boolean shouldScanNotification = true;

    private int scanNotificationDelayMilliseconds = 10_000;

    private int scanNotificationOfStudentOrderDelayMilliseconds = 10_000;

    public long getAccessTokenLifeTimeMinutes() {
        return accessTokenLifeTimeMinutes;
    }

    public void setAccessTokenLifeTimeMinutes(long accessTokenLifeTimeMinutes) {
        this.accessTokenLifeTimeMinutes = accessTokenLifeTimeMinutes;
    }

    public int getScanNotificationOfStudentOrderDelayMilliseconds() {
        return scanNotificationOfStudentOrderDelayMilliseconds;
    }

    public void setScanNotificationOfStudentOrderDelayMilliseconds(int scanNotificationOfStudentOrderDelayMilliseconds) {
        this.scanNotificationOfStudentOrderDelayMilliseconds = scanNotificationOfStudentOrderDelayMilliseconds;
    }

    public long getRefreshTokenLifeTimeMinutes() {
        return refreshTokenLifeTimeMinutes;
    }

    public void setRefreshTokenLifeTimeMinutes(long refreshTokenLifeTimeMinutes) {
        this.refreshTokenLifeTimeMinutes = refreshTokenLifeTimeMinutes;
    }

    public int getScanNotificationDelayMilliseconds() {
        return scanNotificationDelayMilliseconds;
    }

    public void setScanNotificationDelayMilliseconds(int scanNotificationDelayMilliseconds) {
        this.scanNotificationDelayMilliseconds = scanNotificationDelayMilliseconds;
    }

    public boolean isShouldScanNotification() {
        return shouldScanNotification;
    }

    public void setShouldScanNotification(boolean shouldScanNotification) {
        this.shouldScanNotification = shouldScanNotification;
    }
}
