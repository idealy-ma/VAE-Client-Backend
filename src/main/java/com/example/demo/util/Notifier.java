package com.example.demo.util;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

public class Notifier {
    String registrationToken;
    public String getRegistrationToken() {
        return registrationToken;
    }
    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }
    public void sendMessage(String title,String body) throws FirebaseMessagingException{
        Message message = Message.builder().putData("title", title).putData("body", body).setToken(this.getRegistrationToken()).build();
        try {
            FirebaseApp fa = FirebaseApp.initializeApp();
            FirebaseMessaging fm = FirebaseMessaging.getInstance(fa);
            fm.send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
