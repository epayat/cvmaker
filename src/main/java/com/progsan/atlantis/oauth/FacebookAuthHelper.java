package com.progsan.atlantis.oauth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.progsan.atlantis.Gender;
import com.progsan.atlantis.IUserInfo;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//me?fields=name,id,email,gender,birthday, first_name, last_name, verified,  location,locale,name_format,timezone,work,about,picture

/**
 * Created by Erdal on 16.02.2016.
 *
 */
public class FacebookAuthHelper {
    private final Logger LOGGER = LoggerFactory.getLogger(FacebookAuthHelper.class);
    //private String accessToken = "";

    public String buildLoginUrl() {
        String fbLoginUrl = "";
        try {
            fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
                    + Constants.CLIENT_ID_FB + "&redirect_uri="
                    + URLEncoder.encode(Constants.CALLBACK_URI_FB, "UTF-8")
                    + "&scope=public_profile,email,user_location,user_birthday,user_education_history,user_photos,user_website,user_work_history";
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return fbLoginUrl;
    }

    public String getFBGraphUrl(String code) {
        String fbGraphUrl = "";
        try {
            fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
                    + "client_id=" + Constants.CLIENT_ID_FB + "&redirect_uri="
                    + URLEncoder.encode(Constants.CALLBACK_URI_FB, "UTF-8")
                    + "&client_secret=" + Constants.CLIENT_SECRET_FB + "&code=" + code;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fbGraphUrl;
    }

    public String getAccessToken(String code) {
        URL fbGraphURL;
        try {
            fbGraphURL = new URL(getFBGraphUrl(code));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid code received " + e);
        }
        URLConnection fbConnection;
        StringBuffer b = null;
        try {
            fbConnection = fbGraphURL.openConnection();
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(
                    fbConnection.getInputStream()));
            String inputLine;
            b = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                b.append(inputLine + "\n");
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to connect with Facebook "
                    + e);
        }

        String data = b.toString();
        if (data.startsWith("{")) {
            throw new RuntimeException("ERROR: Access Token Invalid: "
                    + data);
        }
        Map<String, String> pairs = splitQuery(data);

        return pairs.get("access_token");
    }

    public static Map<String, String> splitQuery(String query)  {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            try {
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return query_pairs;
    }
    public String getFBGraph(String accessToken) {
        String graph = null;
        try {

            String g = "https://graph.facebook.com/me?access_token=" + accessToken+"&fields=name,id,email,gender,birthday,first_name,last_name,verified,location,locale,name_format,timezone,work,about,picture";
            URL u = new URL(g);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    c.getInputStream()));
            String inputLine;
            StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                b.append(inputLine + "\n");
            in.close();
            graph = b.toString();
            System.out.println(graph);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in getting FB graph data. " + e);
        }
        return graph;
    }

    public Map getGraphData(String fbGraph) {
        Map fbProfile = new HashMap();
        try {
            JSONObject json = new JSONObject(fbGraph);
            fbProfile.put("id", json.getString("id"));
            fbProfile.put("first_name", json.getString("first_name"));
            if (json.has("email"))
                fbProfile.put("email", json.getString("email"));
            if (json.has("gender"))
                fbProfile.put("gender", json.getString("gender"));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in parsing FB graph data. " + e);
        }
        return fbProfile;
    }

    public FBUserInfo getUserInfo(String authCode){
        String accessToken = getAccessToken(authCode);
        String json = getFBGraph(accessToken).replace("\\u0040", "@").replace("googlemail.com", "gmail.com");
        LOGGER.info(json);
        Gson gson = new GsonBuilder().setDateFormat("DD/MM/yyyy").create();
        FBUserInfo userInfo = gson.fromJson(json, FBUserInfo.class);
        return userInfo;
    }

    public class FBUserInfo implements IUserInfo{
        private String id;
        private String email;
        private String first_name;
        private String gender;
        private String name;
        private Date birthday;
        private boolean verified;
        private String last_name;
        private Location location;
        private String locale;
        private String name_format;
        private Integer timezone;
        private Picture picture;
        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getEmail() {
            return email;
        }

        @Override
        public String getGivenName() {
            return first_name;
        }

        @Override
        public String getFamilyName() {
            return getLast_name();
        }

        @Override
        public String getPictureURL() {
            if (this.getPicture() != null && this.getPicture().getData() != null)
                return this.getPicture().getData().getUrl();
            else
                return null;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public boolean isVerified() {
            return verified;
        }

        public void setVerified(boolean verified) {
            this.verified = verified;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public String getName_format() {
            return name_format;
        }

        public void setName_format(String name_format) {
            this.name_format = name_format;
        }

        public Integer getTimezone() {
            return timezone;
        }

        public void setTimezone(Integer timezone) {
            this.timezone = timezone;
        }

        public Picture getPicture() {
            return picture;
        }

        public void setPicture(Picture picture) {
            this.picture = picture;
        }
    }

    public class Location {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Picture {
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }
    public class Data {
        private Boolean is_silhouette;
        private String url;

        public Boolean getIs_silhouette() {
            return is_silhouette;
        }

        public void setIs_silhouette(Boolean is_silhouette) {
            this.is_silhouette = is_silhouette;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
