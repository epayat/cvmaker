package com.progsan.atlantis.oauth;

import org.junit.Test;

/**
 * Created by Erdal on 14.02.2016.
 */
public class GoogleAuthHelperTest {

    @Test
    public void testGetUserInfoJson() throws Exception {
        GoogleAuthHelper authHelper = new GoogleAuthHelper();

        String authCode = "4/ElJbx93Um1yFypgH8I2FUEmnQ0jfyDOhsUAE0PnLKD8#";
        String userInfo = authHelper.getUserInfo(authCode);
        System.out.println(userInfo);
    }
}