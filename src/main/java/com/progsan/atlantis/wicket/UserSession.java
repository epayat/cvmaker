package com.progsan.atlantis.wicket;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * Created by Erdal on 31.01.2016.
 */
public class UserSession extends AuthenticatedWebSession {
    /**
     * Construct.
     *
     * @param request The current request object
     */
    public UserSession(Request request) {
        super(request);
    }

    @Override
    protected boolean authenticate(String username, String password) {
        return false;
    }

    @Override
    public Roles getRoles() {
        return null;
    }
}
