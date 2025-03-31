package com.language.service.service.impl.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import com.language.service.service.abs.notification.NotificationSender;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
public class OneSignalSender implements NotificationSender {

    private static final Logger logger = LoggerFactory.getLogger(OneSignalSender.class);
    private static final String USER_AGENT = "UniBook Backend";
    private static final String URL = "https://onesignal.com/api/v1/notifications";

    private PoolingHttpClientConnectionManager connectionManager;

    private ObjectMapper mapper;
    private OneSignalProperties oneSignalProperties;

    @PostConstruct
    public void init() {
        logger.debug("Initializing connection manager");
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(4);
    }

    @Override
    public NotificationSendResult sendToUser(long userId, OneSignalMessage message) throws IOException {
        CloseableHttpClient client =
                HttpClients
                        .custom()
                        .setConnectionManager(connectionManager)
                        .build();
        HttpPost post = new HttpPost(URL);
        post.addHeader("Authorization", "Basic " + oneSignalProperties.getApiKey());
        post.addHeader("User-Agent", USER_AGENT);
        post.addHeader("Content-Type", ContentType.APPLICATION_JSON.toString());


        /*
         * Try to form a JSON like this:
         *
         * {
         * 	"app_id": "dd299d1f-50fd-497e-9007-xxxxxxxx",
         * 	"filters":[
         *      {"field": "tag", "key": "user_id", "relation": "=", "value": "1"}
         * 	],
         * 	"contents": {
         * 		"en": "..."
         * 	},
         *  "headings": {
         *    "en": "Heading"
         *  }
         * }
         */
        ObjectNode messageNode = mapper.createObjectNode();
        messageNode.put("app_id", oneSignalProperties.getAppId());

        ObjectNode filter = mapper.createObjectNode();
        filter.put("field", "tag");
        filter.put("key", "user_id");
        filter.put("relation", "=");
        filter.put("value", String.valueOf(userId));

        ArrayNode filters = mapper.createArrayNode();
        filters.add(filter);
        messageNode.set("filters", filters);

        ObjectNode contents = mapper.createObjectNode();


        //ObjectNode contentEn = mapper.createObjectNode();
        //contentEn.put("type", message.getType());
        //contentEn.put("content", message.getContent());
        contents.put("en", message.getContent());
        messageNode.set("contents", contents);

        messageNode.set("data", message.getData());

        ObjectNode headings = mapper.createObjectNode();
        headings.put("en", message.getTitle());
        messageNode.set("headings", headings);

        String str = mapper.writeValueAsString(messageNode);
        logger.debug("NotificationId = {}, Now send this message to OneSignal: {}", message.getNotificationId(), str);

        StringEntity entity = new StringEntity(str, StandardCharsets.UTF_8);

        post.setEntity(entity);

        CloseableHttpResponse httpResponse = client.execute(post);
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        String responseBody = EntityUtils.toString(httpResponse.getEntity());
        logger.debug("NotificationId = {}, StatusCode = {}; Response Body: {}", message.getNotificationId(), statusCode, responseBody);

        NotificationSendResult result = new NotificationSendResult();
        result.setHttpStatus(statusCode);
        result.setNotificationId(message.getNotificationId());
        return result;
    }

    @Async
    @Override
    public NotificationSendResult sendToUserAsync(long userId, OneSignalMessage message) throws IOException {
        return sendToUser(userId, message);
    }


    @Autowired
    public void setOneSignalProperties(OneSignalProperties oneSignalProperties) {
        this.oneSignalProperties = oneSignalProperties;
    }

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
