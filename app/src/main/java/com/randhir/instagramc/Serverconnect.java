package com.randhir.instagramc;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

public class Serverconnect extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myappID")
                // if defined
                .clientKey("x5MQuQUrXeT1")
                .server("http://13.59.131.212/parse")
                .build()
        );


        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
