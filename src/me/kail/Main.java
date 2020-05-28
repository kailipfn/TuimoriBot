package me.kail;

import org.apache.commons.lang3.RandomStringUtils;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static Twitter twitter;
    public static void main(String[] args) throws Exception {
        twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer("Consumer", "Secret");
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("上のリンクを開いてPINを入力してね！:");
            String pin = br.readLine();
            try{
                if(pin.length() > 0){
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                }else{
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException te) {
                if(401 == te.getStatusCode()){
                    System.out.println("Unable to get the access token.");
                }else{
                    te.printStackTrace();
                }
            }
        }
        new Task1().start();
    }
    public static Twitter getTwitter() {
        return twitter;
    }
}
class Task1 extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                String random = "@tos \n" + RandomStringUtils.random(10,"あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわをん");
                Main.getTwitter().updateStatus(random);
                Thread.sleep(60000);
            } catch (TwitterException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}