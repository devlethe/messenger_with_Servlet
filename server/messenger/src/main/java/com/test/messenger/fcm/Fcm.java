package com.test.messenger.fcm;

import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

public class Fcm {
	public void Send(String tokenId, String title, String content) {
	        try {    
	            FileInputStream refreshToken = new FileInputStream(Config.JSONPATH);
	            
	            FirebaseOptions options = new FirebaseOptions.Builder()
	                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
	                    .setDatabaseUrl(Config.FIREBASEPATH)
	                    .build();
	            
	            if(FirebaseApp.getApps().isEmpty()) { 
	                FirebaseApp.initializeApp(options);
	            }

	            String registrationToken = tokenId;

	            Message msg = Message.builder()
	                    .setAndroidConfig(AndroidConfig.builder()
	                        .setTtl(3600 * 1000) // 1 hour in milliseconds
	                        .setPriority(AndroidConfig.Priority.NORMAL)
	                        .setNotification(AndroidNotification.builder()
	                            .setTitle(title)
	                            .setBody(content)
	                            .setIcon("stock_ticker_update")
	                            .setColor("#f45342")
	                            .build())
	                        .build())
	                    .setToken(registrationToken)
	                    .build();

	            String response = FirebaseMessaging.getInstance().send(msg);
	            System.out.println("Successfully sent message: " + response);
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	    }
}
