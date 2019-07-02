package com.km086.admin.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class ExtendedJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager entityManager;

    public ExtendedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public EntityManager getEm() {
        return this.entityManager;
    }

    public <S extends T> S merge(S entity) {
        return this.entityManager.merge(entity);
    }

    public <S extends T> S getReference(Class<S> domainClass, ID id) {
        return this.entityManager.getReference(domainClass, id);
    }
}

