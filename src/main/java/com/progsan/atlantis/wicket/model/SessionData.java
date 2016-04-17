package com.progsan.atlantis.wicket.model;

import com.progsan.atlantis.IUserInfo;
import com.progsan.atlantis.jpa.model.CandidateEntity;
import com.progsan.atlantis.jpa.model.CompanyEntity;

/**
 * Created by Erdal on 08.02.2016.
 */
public class SessionData {
    private CompanyEntity companyEntity;

    public SessionData(){
        super();
    }
    private CandidateEntity candidateEntity;
    private IUserInfo userInfo;
    public CandidateEntity getCandidateEntity() {
        return candidateEntity;
    }

    public void setCandidateEntity(CandidateEntity candidateEntity) {
        this.candidateEntity = candidateEntity;
    }

    public IUserInfo getUserInfo(){
        return this.userInfo;
    }
    public void setUserInfo(IUserInfo userInfo){
        this.userInfo = userInfo;
    }

    public CompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }
}
