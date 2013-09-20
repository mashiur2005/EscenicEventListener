package com.cefalo.my.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: escenic
 * Date: 9/18/13
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class FaceBookSender {
    private final Logger logger = Logger.getLogger(getClass());

    private FacebookClient facebookClient;
    private String faceBookId;

    public FaceBookSender(String faceBookId, String appId, String appSecret) {
        setFaceBookId(faceBookId);
        facebookClient = new DefaultFacebookClient();
        facebookClient = new DefaultFacebookClient(facebookClient.obtainAppAccessToken(appId, appSecret).getAccessToken());
    }

    public String getFaceBookId() {
        return faceBookId;
    }

    public void setFaceBookId(String faceBookId) {
        this.faceBookId = faceBookId;
    }

    public String postFeed(String message) {
        FacebookType publishMessageResponse = facebookClient.publish(String.format("%s/feed", getFaceBookId()), FacebookType.class, Parameter.with("message", message));
        logger.debug(String.format("Message published at %s and the Message is : %s", new Date(), message));
        return publishMessageResponse.getId();
    }
}
