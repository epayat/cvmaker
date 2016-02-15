package com.progsan.atlantis.jpa.service;

import com.progsan.atlantis.jpa.model.CandidateEntity;

import javax.persistence.*;

/**
 * Created by Erdal on 14.02.2016.
 */
public class CandidateService {
    private final EntityManagerFactory emf;

    public CandidateService(EntityManagerFactory emf){
        this.emf = emf;
    }
    public CandidateEntity findCandidateEntity(String email){

        EntityManager entityManager = emf.createEntityManager();
        try{
            TypedQuery<CandidateEntity> query = entityManager.createNamedQuery("findByEmail", CandidateEntity.class);
            query.setParameter("email", email);
            try{
                return query.getSingleResult();
            }catch (NoResultException ex){
                return null;
            }
        }finally{
            entityManager.close();
        }
    }

}
