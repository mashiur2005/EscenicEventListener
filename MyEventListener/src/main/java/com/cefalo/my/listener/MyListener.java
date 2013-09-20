package com.cefalo.my.listener;

import com.cefalo.my.facebook.FaceBookSender;
import neo.xredsys.api.Article;
import neo.xredsys.api.IOEvent;
import neo.xredsys.api.services.AsyncEventListenerService;
import org.apache.log4j.Logger;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: escenic
 * Date: 9/16/13
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyListener extends AsyncEventListenerService {
    private final Logger logger = Logger.getLogger(getClass());

    private String facebookId;
    private String appId;
    private String appSecret;

    private FaceBookSender faceBookSender;

    public MyListener() {
        super(false);
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    protected void startEventListener() throws IllegalStateException, IllegalArgumentException, Exception {
        super.startEventListener();
        logger.debug("In StartEventListener.....................");
    }

    @Override
    protected void stopEventListener() throws IllegalStateException, Exception {
        super.stopEventListener();
        logger.debug("In StopEvenetListener.....................");
    }

    @Override
    protected boolean accept(IOEvent ioEvent) throws Exception {
        if (ioEvent.getType() == IOEvent.OBJECT_CREATED) {
            logger.debug(String.format("Created..............................%s", "OBJECT_CREATED"));
            return true;
        }
        return false;
    }

    @Override
    protected void handle(IOEvent ioEvent) throws Exception {
        Article article = (Article) ioEvent.getObject();
        logger.debug(String.format("handling....................%s and title is %s", "OBJECT_CREATED", article.getTitle()));
        faceBookSender = new FaceBookSender(getFacebookId(), getAppId(), getAppSecret());
        URL articleUrl = new URL(article.getUrl());
        faceBookSender.postFeed(String.format("%s created and the url is %s", article.getTitle(), articleUrl));
    }
}
