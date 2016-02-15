package com.progsan.atlantis.wicket.view;

import com.progsan.atlantis.oauth.Constants;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by Erdal on 31.01.2016.
 */
public class HomePage extends BaseWebPage {
    public HomePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize(){
        super.onInitialize();
        //add(new Label("helloMessage", "Hello WicketWorld!"));
        add(new ExternalLink("signInWithGoogle", getAuthCodeLink("google")));
    }

    private IModel<String> getAuthCodeLink(final String provider) {
        return new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if ("google".equalsIgnoreCase(provider)){
                    return String.format("https://accounts.google.com/o/oauth2/auth?response_type=code&redirect_uri=%s&client_id=%s&scope=" +
                            "https://www.googleapis.com/auth/plus.login+https://www.googleapis.com/auth/plus.me+https://www.googleapis.com/auth/userinfo.profile+https://www.googleapis.com/auth/userinfo.email"+
                            "&approval_prompt=force", getRedirectURI(provider), Constants.CLIENT_ID_GOOGLE);
                }else
                    return null;
            }
        };
    }

    private String getRedirectURI(String provider) {
        if ("google".equalsIgnoreCase(provider)){
            return Constants.CALLBACK_URI_GOOGLE;
        }else
            return null;
    }
}
