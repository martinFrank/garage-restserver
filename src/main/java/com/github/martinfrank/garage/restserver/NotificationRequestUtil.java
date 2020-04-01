package com.github.martinfrank.garage.restserver;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class NotificationRequestUtil {

    private static final String PROJECT_ID = "garageapp-f4383";
    private static final String BASE_URL = "https://fcm.googleapis.com";
    private static final String FCM_SEND_ENDPOINT = "/v1/projects/" + PROJECT_ID + "/messages:send";
//    private static final String CONFIG_FILE = "src/main/resources/garageapp-f4383-firebase-admin.json";

    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = {MESSAGING_SCOPE};
    private static final String TITLE_KEY = "title";
    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json; UTF-8";
    private static final String BODY_KEY = "body";
    private static final String NOTIFICATION_KEY = "notification";
    private static final String TOKEN_KEY = "token";
    private static final String TOKEN = "dZ1gWKF9eu0:APA91bHsTaJm_b28iG1WjTU_c6QsRCrXE7t7MWbkvQhgsr3anz80oAAzon9yu0yGkerAfGHKiZL84iI6CHOBl62F7vOOB9YtZujtZ9T7ii1EdHWmCYcAWmiGxmXJpODE7FiJeCsF9cYz";
    private static final String MESSAGE_KEY = "message";

    public static void main(String[] args) throws IOException {
        requestSendingPushNotification("title", "message");
    }

    public static void requestSendingPushNotification(String title, String message) throws IOException {
        JsonObject notificationMessage = buildNotificationMessage(title, message);
        System.out.println("FCM request body for message using common notification object:");
        prettyPrint(notificationMessage);
        sendMessage(notificationMessage);
    }

    private static JsonObject buildNotificationMessage(String title, String message) {
        JsonObject jNotification = new JsonObject();
        jNotification.addProperty(TITLE_KEY, title);
        jNotification.addProperty(BODY_KEY, message);

        JsonObject jMessage = new JsonObject();
        jMessage.add(NOTIFICATION_KEY, jNotification);
        jMessage.addProperty(TOKEN_KEY, TOKEN);

        JsonObject jFcm = new JsonObject();
        jFcm.add(MESSAGE_KEY, jMessage);

        return jFcm;
    }

    private static void prettyPrint(JsonObject jsonObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(jsonObject) + "\n");
    }

    private static void sendMessage(JsonObject fcmMessage) throws IOException {
        HttpURLConnection connection = getConnection();
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(fcmMessage.toString());
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            String response = inputStreamToString(connection.getInputStream());
            System.out.println("Message sent to Firebase for delivery, response:");
            System.out.println(response);
        } else {
            System.out.println("Unable to send message to Firebase:");
            String response = inputStreamToString(connection.getErrorStream());
            System.out.println(response);
        }
    }

    private static HttpURLConnection getConnection() throws IOException {
        URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty(AUTHORIZATION_KEY, "Bearer " + getAccessToken());
        httpURLConnection.setRequestProperty(CONTENT_TYPE_KEY, CONTENT_TYPE_JSON);
        return httpURLConnection;
    }

    private static String getAccessToken() throws IOException {
//        GoogleCredentials googleCredential = GoogleCredentials
//                .fromStream(new FileInputStream(CONFIG_FILE))
//                .createScoped(Arrays.asList(SCOPES));
        GoogleCredentials googleCredential = GoogleCredentials
                .fromStream(new ByteArrayInputStream(Bin.unBin().getBytes()))
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshIfExpired();
        return googleCredential.getAccessToken().getTokenValue();
    }

    private static String inputStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine());
        }
        return stringBuilder.toString();
    }

}
