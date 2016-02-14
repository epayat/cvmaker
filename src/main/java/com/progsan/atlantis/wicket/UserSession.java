package com.progsan.atlantis.wicket;

import com.progsan.atlantis.jpa.model.CandidateEntity;
import com.progsan.atlantis.jpa.service.CandidateService;
import com.progsan.atlantis.jpa.service.LogonService;
import com.progsan.atlantis.IUserInfo;
import com.progsan.atlantis.wicket.model.SessionData;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * Created by Erdal on 31.01.2016.
 */
public class UserSession extends AuthenticatedWebSession {
    private final SessionData sessionData = new SessionData();


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
        LogonService logonService = new LogonService();
        authenticateUser(logonService.authenticate(username, password));
        return sessionData.getUserInfo() != null;
    }

    @Override
    public Roles getRoles() {
        return null;
    }

    public SessionData getSessionData() {
        return this.sessionData;
    }

    public void authenticateUser(IUserInfo userInfo) {
        sessionData.setUserInfo(userInfo);
        CandidateService candidateService = new CandidateService();
        CandidateEntity candidateEntity = candidateService.findCandidateEntity(userInfo.getEmail());
        sessionData.setCandidateEntity(candidateEntity);
    }
    public IUserInfo getUserInfo(){
        return sessionData.getUserInfo() ;
    }
}
