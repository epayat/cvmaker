package com.progsan.atlantis;

/**
 * Created by Erdal on 14.02.2016.
 */
public interface IUserInfo {
    String getId();
    String getEmail();
    String getGivenName();
    String getFamilyName();
    String getPictureURL();
    Gender getGenderAsEnum();
}
