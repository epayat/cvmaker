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

    public CandidateEntity save(CandidateEntity candidateEntity) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            if (candidateEntity.getCandidateId() == null){
                entityManager.persist(candidateEntity);
                entityManager.getTransaction().commit();
                return candidateEntity;
            } else{
                CandidateEntity res = entityManager.merge(candidateEntity);
                entityManager.getTransaction().commit();
                return res;
            }

        }catch (Exception e){
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw e;
        }finally{
            entityManager.close();
        }
    }
}
