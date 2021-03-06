package com.progsan.atlantis.oauth;

import com.progsan.atlantis.Gender;
import com.progsan.atlantis.IUserInfo;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by Erdal on 14.02.2016.
 */
public class GoogleUserInfo implements Serializable, IUserInfo {
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FacebookAuthHelper.class);
    private String id;
    private String email;
    private boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String gender;
    private String locale;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getGivenName() {
        return getGiven_name();
    }

    @Override
    public String getFamilyName() {
        return getFamily_name();
    }

    @Override
    public String getPictureURL() {
        return getPicture();
    }

    @Override
    public Gender getGenderAsEnum() {
        if (gender != null){
            if (gender.equalsIgnoreCase("male"))
                return Gender.MALE;
            else if (gender.equalsIgnoreCase("female"))
                return Gender.MALE;
            else {
                LOGGER.warn("unknown gender text:" + gender);
                return Gender.UNKNOWN;
            }
        }else
            return Gender.UNKNOWN;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified_email() {
        return verified_email;
    }

    public void setVerified_email(boolean verified_email) {
        this.verified_email = verified_email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    @Override
    public String toString() {
        return "GoogleUserInfo [id=" + id + ", email=" + email
                + ", verified_email=" + verified_email + ", name=" + name
                + ", given_name=" + given_name + ", family_name=" + family_name
                + "]";
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
