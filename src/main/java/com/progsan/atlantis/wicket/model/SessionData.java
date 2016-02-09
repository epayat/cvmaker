package com.progsan.atlantis.wicket.model;

import com.progsan.atlantis.jpa.model.CandidateEntity;

/**
 * Created by Erdal on 08.02.2016.
 */
public class SessionData {
    private CandidateEntity candidateEntity;

    public CandidateEntity getCandidateEntity() {
        return candidateEntity;
    }

    public void setCandidateEntity(CandidateEntity candidateEntity) {
        this.candidateEntity = candidateEntity;
    }
}
