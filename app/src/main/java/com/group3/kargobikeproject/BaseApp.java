package com.group3.kargobikeproject;

import android.app.Application;

import com.group3.kargobikeproject.Model.Repository.UserRepository;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public UserRepository getUserRepository() {
        return UserRepository.getInstance();
    }

}
