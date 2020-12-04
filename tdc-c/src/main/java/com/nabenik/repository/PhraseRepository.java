package com.nabenik.repository;

import com.nabenik.model.Phrase;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Phrase.class)
public interface PhraseRepository extends EntityRepository<Phrase, Long> {

    //@PersistenceContext(unitName = "test")
    //private EntityManager entityManager;


}
