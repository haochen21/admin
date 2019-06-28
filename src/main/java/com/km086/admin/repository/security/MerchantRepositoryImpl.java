package com.km086.admin.repository.security;


import com.km086.admin.model.security.Merchant;
import com.km086.admin.model.security.MerchantFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


public class MerchantRepositoryImpl   implements MerchantRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public List<Merchant> findByFilter(MerchantFilter filter, Integer startIndex, Integer pageSize) {
        List<Merchant> merchants = new ArrayList();

        TypedQuery<Merchant> query = filter.createListQuery(this.em);
        if ((startIndex != null) && (pageSize != null)) {
            query.setFirstResult(startIndex.intValue() * pageSize.intValue());
            query.setMaxResults(pageSize.intValue());
        }
        List<Merchant> dbMerchants = query.getResultList();
        if ((dbMerchants != null) && (dbMerchants.size() > 0)) {
            merchants.addAll(dbMerchants);
        }
        return merchants;
    }

    public Long countByFilter(MerchantFilter filter) {
        TypedQuery<Long> query = filter.createCountQuery(this.em);
        Long count = query.getSingleResult();
        return count;
    }
}

