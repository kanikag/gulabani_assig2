package com.hellokoding.auth.social;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Service
public class FacebookConnectionService {
    public static final String FB_APP_ID = "460946734518075";
    public static final String FB_APP_SECRET = "9e1de92a18a6469e13849ee90b12b784";
    public static final String REDIRECT_URI = "http://localhost:8080/facebooklogin";


    public static final String SPOTIFY_APP_ID = "46c4c2487589493e9435f73e216c58bf";
    public static final String SPOTIFY_APP_SECRET = "570acedf36ef4146ad5359c5e698fb6b";
    public static final String SPOTIFY_REDIRECT_URI = "http://localhost:8080/spotifylogin";

    static String accessToken = "";

    public String getFBAuthUrl() {
        String fbLoginUrl = "";
        try {
            fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
                    + FacebookConnectionService.FB_APP_ID + "&redirect_uri="
                    + URLEncoder.encode(FacebookConnectionService.REDIRECT_URI, "UTF-8")
                    + "&scope=email";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fbLoginUrl;
    }

    public String getSpotifyAuthUrl() {
        String spotifyUrl = "";
        try {
            spotifyUrl = "https://accounts.spotify.com/authorize?client_id="
                    + FacebookConnectionService.SPOTIFY_APP_ID + "&response_type=code&redirect_uri="
                    + URLEncoder.encode(FacebookConnectionService.SPOTIFY_REDIRECT_URI, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return spotifyUrl;
    }

    public String getFBGraphUrl(String code) {
        String fbGraphUrl = "";
        try {
            fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
                    + "client_id=" + FacebookConnectionService.FB_APP_ID + "&redirect_uri="
                    + URLEncoder.encode(FacebookConnectionService.REDIRECT_URI, "UTF-8")
                    + "&client_secret=" + FB_APP_SECRET + "&code=" + code;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fbGraphUrl;
    }

    public String getAccessToken(String code) {
        if ("".equals(accessToken)) {
            URL fbGraphURL;
            try {
                fbGraphURL = new URL(getFBGraphUrl(code));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Invalid code received " + e);
            }
            URLConnection fbConnection;
            StringBuffer b = null;
            try {
                fbConnection = fbGraphURL.openConnection();
                BufferedReader in;
                in = new BufferedReader(new InputStreamReader(
                        fbConnection.getInputStream()));
                String inputLine;
                b = new StringBuffer();
                while ((inputLine = in.readLine()) != null)
                    b.append(inputLine + "\n");
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to connect with Facebook "
                        + e);
            }

            JSONObject json = new JSONObject(b.toString());
            System.out.println(json);
            if (json.has("access_token")) {
                accessToken = json.getString("access_token").toString();
            } else
                accessToken = b.toString();
            System.out.println("Accesss Token " + accessToken);
            if (accessToken.startsWith("{")) {
                throw new RuntimeException("ERROR: Access Token Invalid: "
                        + accessToken);
            }
        }
        return accessToken;
    }
}
