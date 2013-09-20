package com.cefalo.my.facebook;

import com.restfb.*;
import com.restfb.types.FacebookType;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpHeaders;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: escenic
 * Date: 9/17/13
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    private static String appId = "664722773540420";
    private static String appSecret = "8115b5095358acc081633b072a5c5aa7";

    public static void main(String args[]) {
        String fbToken = "CAAJcj9TKfkQBAEhNeVDtcu59io0cIRSSbkx551nz5SHMzD39OUbbg8ZBRknE6ZClB07gULDpuSB8iuVn7ZAPn15vgZA1ZC7FRZCeMbZAEZAzJr54JVZBUwUiVTZBKIuBdrrEIh823j9CwXgAQwaGHdSJB443FVHdHt0omQhjp1azjZBKScmPT2AQIz6";
        FacebookClient facebookClient = new DefaultFacebookClient(fbToken);
        String requestUrl = String.format("https://graph.facebook.com/oauth/authorize?client_id=%s&redirect_uri=http://www.cefalo.com.bd/connect/login_success.html&scope=publish_stream,create_event", appId);
        /*reqRecursive(requestUrl, 0);*/
        System.out.println(facebookClient.obtainAppAccessToken(appId, appSecret));
        facebookClient = new DefaultFacebookClient(facebookClient.obtainAppAccessToken(appId, appSecret).getAccessToken());
        URL url = null;
        try {
            url = new URL("http://ece-mashiur:8080/demo/incoming/article42.ece");
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        FacebookType publishMessageResponse = facebookClient.publish("mashiur.rahman.16121/feed", FacebookType.class, Parameter.with("message", String.format("Message at %s and %s", new Date().toString(), url)));
        System.out.println("Published message ID: " + publishMessageResponse.getId());

        /*try(InputStream inputStream = new FileInputStream(new File("/home/mashiur/Downloads/test.jpg"))) {
            FacebookType publishPhotoResponse = facebookClient.publish("mashiur.rahman.16121/photos", FacebookType.class,
                    BinaryAttachment.with("cat.png", inputStream),Parameter.with("message", "Test cat"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/


    }

    public static void reqRecursive(String requestUrl, int counter) {
        System.out.println(counter + ".....................");
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(requestUrl);
        /*NameValuePair userName = new NameValuePair("username", "mashiur.rahman.16121");
        NameValuePair password = new NameValuePair("password", "Mashiur1");
        getMethod.setQueryString(new NameValuePair[]{userName, password});*/
        getMethod.setFollowRedirects(false);
        try {
            int statuscode = client.executeMethod(getMethod);
            System.out.println("Status Code " + statuscode + " URI " + getMethod.getURI());
            for (Header header : getMethod.getResponseHeaders()) {
                if (header.getName().equals(HttpHeaders.LOCATION)) {
                    System.out.println("Header name = " + header.getName() + ", value = " + header.getValue());
                    requestUrl = header.getValue();
                }
            }
            if (statuscode == 302) {
                reqRecursive(requestUrl, counter++);
            } else {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
