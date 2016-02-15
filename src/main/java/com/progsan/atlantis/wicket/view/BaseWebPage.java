package com.progsan.atlantis.wicket.view;

import com.progsan.atlantis.wicket.UserSession;
import com.progsan.atlantis.wicket.model.SessionData;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.persistence.EntityManagerFactory;

/**
 * Created by Erdal on 31.01.2016.
 */
public class BaseWebPage extends WebPage {
    public BaseWebPage(PageParameters parameters) {
        super(parameters);
    }

    public SessionData getSessionData(){
        return getUserSession().getSessionData();
    }

    public UserSession getUserSession() {
        return (UserSession)Session.get();
    }


    protected EntityManagerFactory getEntityManagerFactory() {
        return getUserSession().getEntityManagerFactory();
    }
}
