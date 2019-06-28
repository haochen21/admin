package com.km086.admin.model.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AgentBillFilter implements Serializable {

    private Date statBeginDateBefore;

    private Date statBeginDateAfter;

    private Long userId;

    private Collection<BillStatus> statuses;

    private int page;

    private int size;

    private static final long serialVersionUID = -3781522568411885697L;

    public TypedQuery<Long> createCountQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<AgentBill> agentBill = criteria.from(AgentBill.class);
        criteria.select(cb.countDistinct(agentBill.get("id")));
        List<Predicate> predicates = getPredicates(cb, criteria, agentBill);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> query = em.createQuery(criteria);
        return query;
    }

    public TypedQuery<AgentBill> createListQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AgentBill> criteria = cb.createQuery(AgentBill.class);
        Root<AgentBill> agentBill = criteria.from(AgentBill.class);
        criteria.select(agentBill);
        criteria.distinct(true);

        List<Predicate> predicates = getPredicates(cb, criteria, agentBill);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        criteria.orderBy(new Order[]{cb.desc(agentBill.get("statBeginDate"))});
        TypedQuery<AgentBill> query = em.createQuery(criteria);
        return query;
    }

    private <T> List<Predicate> getPredicates(CriteriaBuilder cb, CriteriaQuery<T> criteria, Root<AgentBill> root) {
        List<Predicate> predicates = new ArrayList();

        if (this.userId != null) {
            predicates.add(cb.equal(root.get("user").get("id"), this.userId));
        }

        if (this.statuses != null) {
            if (this.statuses.size() > 1) {
                CriteriaBuilder.In<BillStatus> statusPredicate = cb.in(root.get("status"));
                for (BillStatus status : this.statuses) {
                    statusPredicate.value(status);
                }
                predicates.add(statusPredicate);
            } else {
                predicates.add(cb.equal(root.get("status"), this.statuses.iterator().next()));
            }
        }

        if ((this.statBeginDateAfter != null) && (this.statBeginDateBefore != null)) {
            predicates.add(cb.between(root.get("statBeginDate"), this.statBeginDateAfter, this.statBeginDateBefore));
        } else if (this.statBeginDateAfter != null) {
            predicates.add(cb.greaterThan(root.get("statBeginDate"), this.statBeginDateAfter));
        } else if (this.statBeginDateBefore != null) {
            predicates.add(cb.lessThan(root.get("statBeginDate"), this.statBeginDateBefore));
        }

        return predicates;
    }
}
