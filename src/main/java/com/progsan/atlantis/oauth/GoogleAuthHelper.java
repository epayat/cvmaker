package com.progsan.atlantis.oauth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * A helper class for Google's OAuth2 authentication API.
 */
public final class GoogleAuthHelper {
    private final Logger LOGGER = LoggerFactory.getLogger(GoogleAuthHelper.class);
    // start google authentication constants
    private static final Iterable<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/plus.login;https://www.googleapis.com/auth/plus.me;https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    // end google authentication constants

    private String stateToken;

    private final GoogleAuthorizationCodeFlow flow;

    /**
     * Constructor initializes the Google Authorization Code Flow with CLIENT ID, SECRET, and SCOPE
     */
    public GoogleAuthHelper() {
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
                JSON_FACTORY, Constants.CLIENT_ID_GOOGLE, Constants.CLIENT_SECRET_GOOGLE, SCOPE).build();
        generateStateToken();
    }

    /**
     * Builds a login URL based on client ID, secret, callback URI, and scope
     */
    public String buildLoginUrl() {

        final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();

        return url.setRedirectUri(Constants.CALLBACK_URI_GOOGLE).setState(stateToken).build();
    }

    /**
     * Generates a secure state token
     */
    private void generateStateToken(){
        SecureRandom sr1 = new SecureRandom();

        stateToken = "google;"+sr1.nextInt();
    }

    /**
     * Accessor for state token
     */
    public String getStateToken(){
        return stateToken;
    }

    /**
     * Expects an Authentication Code, and makes an authenticated request for the user's profile information
     * @return user profile information
     * @param authCode authentication code provided by google
     */
    public GoogleUserInfo getUserInfo(final String authCode) throws IOException {

        final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(Constants.CALLBACK_URI_GOOGLE).execute();
        final Credential credential = flow.createAndStoreCredential(response, null);
        final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
        // Make an authenticated request
        final GenericUrl url = new GenericUrl(USER_INFO_URL);
        final HttpRequest request = requestFactory.buildGetRequest(url);
        request.setParser(new JsonObjectParser(new GsonFactory()));
        request.getHeaders().setContentType("application/json");
        HttpResponse userInfoResponse = request.execute();
        String json = userInfoResponse.parseAsString();
        LOGGER.info(json);
        Gson gson = new GsonBuilder().create();
        GoogleUserInfo userInfo = gson.fromJson(json, GoogleUserInfo.class);
        return userInfo;
    }
}