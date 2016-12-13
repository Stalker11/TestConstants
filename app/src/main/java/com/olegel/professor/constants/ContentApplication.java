package com.olegel.professor.constants;

import android.app.Application;

public class ContentApplication extends Application {
    @Override
    public void onCreate() {
        ParserXml.setContext(getApplicationContext());
        super.onCreate();
    }
}
