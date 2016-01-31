package com.progsan;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Erdal on 31.01.2016.
 */
public class JPATest {
    private EntityManagerFactory entityManagerFactory;
    @Before
    public void setUp(){
        entityManagerFactory = Persistence.createEntityManagerFactory( "atlantis" );
    }
    @Test
    public void testConnection(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Assert.assertTrue(entityManager.isOpen());
    }
}
