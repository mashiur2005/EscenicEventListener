package com.cefalo.my.facebook;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: escenic
 * Date: 9/17/13
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class FaceBookAuthToken {
    private static final String NETWORK_NAME = "Facebook";
    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
    private static final Token EMPTY_TOKEN = null;

    public static void main(String args[]) {
        String appId = "664722773540420";
        String appSecret = "8115b5095358acc081633b072a5c5aa7";
        /*String url1 = String.format("https://graph.facebook.com/oauth/authorize?client_id=%s&redirect_uri=http://www.cefalo.com.bd/connect/login_success.html&scope=publish_stream,create_event", appId);
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(url1);
        try {
            int statuscode = client.executeMethod(getMethod);
            System.out.println("Status code is : " + statuscode + ".................." + getMethod.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/
        scribeTest();
    }

    public static void scribeTest() {
        String apiKey = "664722773540420";
        String apiSecret = "8115b5095358acc081633b072a5c5aa7";
        OAuthService service = new ServiceBuilder()
                .provider(FacebookApi.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback("http://www.cefalo.com.bd/oauth_callback/")
                .build();
        Scanner in = new Scanner(System.in);

        System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
        System.out.println();

        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize Scribe here:");
        System.out.println(authorizationUrl);
        System.out.println("And paste the authorization code here");
        System.out.print(">>");
        Verifier verifier = new Verifier(in.nextLine());
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken.getToken() + " )");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());

        System.out.println();
        System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
    }
}
