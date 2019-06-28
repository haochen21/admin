package com.km086.admin.repository.account;

import com.km086.admin.model.account.Bill;
import com.km086.admin.model.account.BillFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class BillRepositoryImpl implements BillRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public List<Bill> findByFilter(BillFilter filter, Integer startIndex, Integer pageSize) {
        List<Bill> bills = new ArrayList();

        TypedQuery<Bill> query = filter.createListQuery(this.em);
        if ((startIndex != null) && (pageSize != null)) {
            query.setFirstResult(startIndex.intValue() * pageSize.intValue());
            query.setMaxResults(pageSize.intValue());
        }
        List<Bill> dbBills = query.getResultList();
        if ((dbBills != null) && (dbBills.size() > 0)) {
            bills.addAll(dbBills);
        }
        return bills;
    }

    public Long countByFilter(BillFilter filter) {
        TypedQuery<Long> query = filter.createCountQuery(this.em);
        Long count = query.getSingleResult();
        return count;
    }

    public BigDecimal statAgentEarning(BillFilter filter) {
        TypedQuery<BigDecimal> query = filter.createAgentEarning(this.em);
        List<BigDecimal> result = query.getResultList();
        if ((result != null) && (result.size() > 0)) {
            return result.get(0);
        }
        return new BigDecimal(0);
    }


    public BigDecimal statTicketEarning(BillFilter filter) {
        TypedQuery<BigDecimal> query = filter.createTicketEarning(this.em);
        List<BigDecimal> result = query.getResultList();
        if ((result != null) && (result.size() > 0)) {
            return result.get(0);
        }
        return new BigDecimal(0);
    }
}