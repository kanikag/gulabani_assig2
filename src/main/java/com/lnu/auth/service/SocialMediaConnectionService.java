package com.lnu.auth.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Service
public class SocialMediaConnectionService {
    public static final String FB_APP_ID = "758161844622100";
    public static final String FB_APP_SECRET = "6b9cd1a8825aa2e68e814b20baa1d26d";
    public static final String REDIRECT_URI = "https://floating-taiga-98963.herokuapp.com/facebooklogin";


    public static final String SPOTIFY_APP_ID = "46c4c2487589493e9435f73e216c58bf";
    public static final String SPOTIFY_APP_SECRET = "570acedf36ef4146ad5359c5e698fb6b";
    public static final String SPOTIFY_REDIRECT_URI = "https://floating-taiga-98963.herokuapp.com/spotifylogin";

    public static final String GITHUB_APP_ID = "36eba14c6767eac8cce5";

    @Autowired
    RestTemplate restTemplate;

    public String getFBAuthUrl() {
        String fbLoginUrl = "";
        try {
            fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
                    + SocialMediaConnectionService.FB_APP_ID + "&redirect_uri="
                    + URLEncoder.encode(SocialMediaConnectionService.REDIRECT_URI, "UTF-8")
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
                    + SocialMediaConnectionService.SPOTIFY_APP_ID + "&response_type=code&redirect_uri="
                    + URLEncoder.encode(SocialMediaConnectionService.SPOTIFY_REDIRECT_URI, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return spotifyUrl;
    }

    public String getGithubUrl() {
        return "https://github.com/login/oauth/authorize?scope=user:email&client_id="
                + SocialMediaConnectionService.GITHUB_APP_ID;
    }

    public String getFBGraphUrl(String code) {
        String fbGraphUrl = "";
        try {
            fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
                    + "client_id=" + SocialMediaConnectionService.FB_APP_ID + "&redirect_uri="
                    + URLEncoder.encode(SocialMediaConnectionService.REDIRECT_URI, "UTF-8")
                    + "&client_secret=" + FB_APP_SECRET + "&code=" + code;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fbGraphUrl;
    }

    public String getAccessToken(String code) {
        String accessToken = "";
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
            if (json.has("access_token")) {
                accessToken = json.getString("access_token").toString();
            } else
                accessToken = b.toString();
            if (accessToken.startsWith("{")) {
                throw new RuntimeException("ERROR: Access Token Invalid: "
                        + accessToken);
            }
        }
        return accessToken;
    }
}
