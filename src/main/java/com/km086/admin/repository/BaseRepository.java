package com.km086.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID> {

    EntityManager getEm();

    <S extends T> S merge(S paramS);

    <S extends T> S getReference(Class<S> paramClass, ID paramID);
}

