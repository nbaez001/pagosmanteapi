package gob.pe.essalud.pagosmanteapi.config;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class JpaGenericRepository {
    @PersistenceContext
    EntityManager entityManager;

    public EntityManager createEntityManager() { return entityManager;}

}
