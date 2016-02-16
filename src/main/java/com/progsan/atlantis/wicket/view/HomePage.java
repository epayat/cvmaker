package com.progsan.atlantis.wicket.view;

import com.progsan.atlantis.oauth.Constants;
import com.progsan.atlantis.oauth.FacebookAuthHelper;
import com.progsan.atlantis.oauth.GoogleAuthHelper;
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
        add(new ExternalLink("signInWithFB", getAuthCodeLink("facebook")));
    }

    private IModel<String> getAuthCodeLink(final String provider) {
        return new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if ("google".equalsIgnoreCase(provider)){
                    GoogleAuthHelper authHelper = new GoogleAuthHelper();
                    return authHelper.buildLoginUrl();
                }else if ("facebook".equalsIgnoreCase(provider)){
                    FacebookAuthHelper authHelper = new FacebookAuthHelper();
                    return authHelper.buildLoginUrl();
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
