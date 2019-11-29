package com.example.kargobikeproject;

import android.app.Application;

import com.example.kargobikeproject.Model.Repository.UserRepository;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public UserRepository getUserRepository() {
        return UserRepository.getInstance();
    }

}
