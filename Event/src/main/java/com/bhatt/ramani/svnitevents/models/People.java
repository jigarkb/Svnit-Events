package com.bhatt.ramani.svnitevents.models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.net.URL;

public class People implements Serializable {
	
	private String bio;
	private String role;
	private String name;
	private String twitterHandle;
	private String peopleBio;
    private String contactno;

    public String getPhone() {
        return contactno;
    }

    public void setPhone(String contactno) {
        this.contactno = contactno;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        if ("null".equals(twitterHandle)) {
            return;
        }
        this.twitterHandle = twitterHandle;
    }

    public String getPeopleBio() {
        return peopleBio;
    }

    public void setPeopleBio(String peopleBio) {
        this.peopleBio = peopleBio;
    }

    @Override
    public String toString() {
        return getName();
    }
}
