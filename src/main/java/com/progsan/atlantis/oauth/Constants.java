package com.progsan.atlantis.oauth;

/**
 * Created by Erdal on 14.02.2016.
 */
public class Constants {
    public static final String CLIENT_ID_FB = "209487559404864";
    public static final String CLIENT_SECRET_FB = "a31239d9481ef7eb4ee477133af11807";
    public static final String CALLBACK_URI_FB = "http://localhost:8080/fb-signed-in";

    public static final String CLIENT_ID_LINKEDIN = "77lb8qg8hxgwvt";
    public static final String CLIENT_SECRET_LINKEDID = "D1wHJXSeRcdN3YUZ";

    public static final String CLIENT_ID_GOOGLE = "672113283355-adb0al2rtg9gkl6g8n6j8o493vkle4ip.apps.googleusercontent.com";
    public static final String CLIENT_SECRET_GOOGLE = "PB-h0FGtUh2EUY4f9z1VIv1g";
    /**
     * Callback URI that google will redirect to after successful authentication
     */
    public static final String CALLBACK_URI_GOOGLE = "http://localhost:8080/google-signed-in";
}
