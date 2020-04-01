package com.github.martinfrank.garage.restserver;

import java.io.IOException;

public class SendNotificationAction extends SubmitAction {

    @Override
    public void apply() {
        System.out.println("i'm an action, i apply now");
        try {
            NotificationRequestUtil.requestSendingPushNotification("title", "message");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
