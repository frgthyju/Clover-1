package com.clover.utils;

import android.app.Application;

import com.clover.entities.Relationship;
import com.clover.entities.User;

/**
 * Created by zpfang on 2015/6/30.
 */
public class CloverApplication extends Application{

    private User m_user;

    public User getM_user() {
        return m_user;
    }

    public void setM_user(User m_user) {
        this.m_user = m_user;
    }
}
