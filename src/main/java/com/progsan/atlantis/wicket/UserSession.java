package com.progsan.atlantis.wicket;

import com.progsan.atlantis.jpa.model.CandidateEntity;
import com.progsan.atlantis.jpa.service.CandidateService;
import com.progsan.atlantis.jpa.service.LogonService;
import com.progsan.atlantis.IUserInfo;
import com.progsan.atlantis.wicket.model.SessionData;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Erdal on 31.01.2016.
 */
public class UserSession extends AuthenticatedWebSession {
    private final Logger LOGGER = LoggerFactory.getLogger(UserSession.class);
    private final SessionData sessionData = new SessionData();
    private EntityManagerFactory entityManagerFactory;

    /**
     * Construct.
     *
     * @param request The current request object
     */
    public UserSession(Request request) {
        super(request);
        this.bind();
        LOGGER.info(String.format("new session created. SessionId: %s", this.getId()));
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
        CandidateService candidateService = new CandidateService(getEntityManagerFactory());
        CandidateEntity candidateEntity = candidateService.findCandidateEntity(userInfo.getEmail());
        if (candidateEntity == null){
            candidateEntity = new CandidateEntity();
            candidateEntity.setEmail(userInfo.getEmail());
            candidateEntity.setFirstName(userInfo.getGivenName());
            candidateEntity.setLastName(userInfo.getFamilyName());
        }
        candidateEntity.addPictureIfNotSet(userInfo.getPictureURL());
        sessionData.setCandidateEntity(candidateEntity);
    }
    public IUserInfo getUserInfo(){
        return sessionData.getUserInfo() ;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory("atlantis");
        }
        return entityManagerFactory;
    }
    @Override
    public void signOut(){
        super.signOut();
        entityManagerFactory.close();
        entityManagerFactory = null;
    }
}
