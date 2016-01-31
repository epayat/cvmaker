package com.progsan.atlantis.wicket;

import com.progsan.atlantis.wicket.view.IndexPage;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;

/**
 * Created by Erdal on 31.01.2016.
 */
public class WicketApplication extends AuthenticatedWebApplication {
    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return UserSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return null;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return IndexPage.class;
    }

    @Override
    public void init(){
        super.init();
        mountPages();
    }

    private void mountPages(){
        mountPage("/", getHomePage());
    }
}
