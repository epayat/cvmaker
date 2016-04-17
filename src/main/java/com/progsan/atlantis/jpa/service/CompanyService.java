package com.progsan.atlantis.jpa.service;

import com.progsan.atlantis.jpa.model.CompanyEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by Erdal on 17.04.2016.
 */
public class CompanyService {
    private final EntityManagerFactory emf;

    public CompanyService(EntityManagerFactory entityManagerFactory) {
        super();
        this.emf = entityManagerFactory;
    }

    public CompanyEntity save(CompanyEntity companyEntity) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try{
            if (companyEntity.getLogo() != null){
                if (companyEntity.getLogo().getImageId() == null)
                    entityManager.persist(companyEntity.getLogo());
                else
                    companyEntity.setLogo(entityManager.merge(companyEntity.getLogo()));
            }
            if (companyEntity.getIndustry() != null){
                if (companyEntity.getIndustry().getIndustryId() == null)
                    entityManager.persist(companyEntity.getIndustry());
                else
                    companyEntity.setIndustry(entityManager.merge(companyEntity.getIndustry()));
            }
            if (companyEntity.getCompanyId() == null){
                entityManager.persist(companyEntity);
                entityManager.getTransaction().commit();
                return companyEntity;
            } else{
                CompanyEntity res = entityManager.merge(companyEntity);
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
