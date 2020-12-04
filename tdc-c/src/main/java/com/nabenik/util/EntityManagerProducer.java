package com.nabenik.util;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.interceptor.Interceptor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
@Priority(Interceptor.Priority.APPLICATION)
public class EntityManagerProducer
{

    @PersistenceUnit
    EntityManagerFactory emf;

    @PostConstruct
    public void init(){
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory("test");
        }
    }

    @Produces
    @Default
    public EntityManagerFactory createEntityManagerFactory() {
        return emf;
    }

    @Produces
    @Default
    @Dependent
    public EntityManager createEntityManager() {
        return this.emf.createEntityManager();
    }

    public void close(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }
}