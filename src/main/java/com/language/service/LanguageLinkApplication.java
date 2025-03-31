package com.language.service;

import com.language.service.config.ApplicationProperties;
import com.language.service.config.SpringFoxProperties;
import com.language.service.service.impl.notification.OneSignalProperties;
import com.language.service.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties(
        value = {
                ApplicationProperties.class,
                StorageProperties.class,
                OneSignalProperties.class,
                SpringFoxProperties.class
        })
public class LanguageLinkApplication {

    private static ApplicationContext applicationContext;
    private final Object lock = new Object();

    public static void main(String[] args) {
        SpringApplication.run(LanguageLinkApplication.class, args);
    }

    public static synchronized ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        synchronized (lock) {
            LanguageLinkApplication.applicationContext = applicationContext;
        }
    }

}
