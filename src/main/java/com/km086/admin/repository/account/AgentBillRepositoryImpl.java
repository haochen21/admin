package com.km086.admin.repository.account;

import com.km086.admin.model.account.AgentBill;
import com.km086.admin.model.account.AgentBillFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


public class AgentBillRepositoryImpl implements AgentBillRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public List<AgentBill> findByFilter(AgentBillFilter filter, Integer startIndex, Integer pageSize) {
        List<AgentBill> bills = new ArrayList();

        TypedQuery<AgentBill> query = filter.createListQuery(this.em);
        if ((startIndex != null) && (pageSize != null)) {
            query.setFirstResult(startIndex.intValue() * pageSize.intValue());
            query.setMaxResults(pageSize.intValue());
        }
        List<AgentBill> dbBills = query.getResultList();
        if ((dbBills != null) && (dbBills.size() > 0)) {
            bills.addAll(dbBills);
        }
        return bills;
    }

    public Long countByFilter(AgentBillFilter filter) {
        TypedQuery<Long> query = filter.createCountQuery(this.em);
        Long count = query.getSingleResult();
        return count;
    }
}

