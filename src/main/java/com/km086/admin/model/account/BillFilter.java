package com.km086.admin.model.account;

import com.km086.admin.model.security.Merchant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BillFilter implements Serializable {

    private Date statDateBefore;

    private Date statDateAfter;

    private Date statDate;

    private Long merchantId;

    private Long userId;

    private Collection<BillStatus> statuses;

    private int page;

    private int size;

    private static final long serialVersionUID = -3781522568411885697L;


    public TypedQuery<Long> createCountQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<Bill> bill = criteria.from(Bill.class);
        criteria.select(cb.countDistinct(bill.get("id")));
        List<Predicate> predicates = getPredicates(cb, criteria, bill);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> query = em.createQuery(criteria);
        return query;
    }

    public TypedQuery<Bill> createListQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bill> criteria = cb.createQuery(Bill.class);
        Root<Bill> bill = criteria.from(Bill.class);
        criteria.select(bill);
        criteria.distinct(true);

        List<Predicate> predicates = getPredicates(cb, criteria, bill);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        criteria.orderBy(new Order[]{cb.desc(bill.get("statDate"))});
        TypedQuery<Bill> query = em.createQuery(criteria);
        return query;
    }

    private <T> List<Predicate> getPredicates(CriteriaBuilder cb, CriteriaQuery<T> criteria, Root<Bill> root) {
        List<Predicate> predicates = new ArrayList();

        if (this.merchantId != null) {
            predicates.add(cb.equal(root.get("merchant").get("id"), this.merchantId));
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

        if ((this.statDateAfter != null) && (this.statDateBefore != null)) {
            predicates.add(cb.between(root.get("statDate"), this.statDateAfter, this.statDateBefore));
        } else if (this.statDateAfter != null) {
            predicates.add(cb.greaterThan(root.get("statDate"), this.statDateAfter));
        } else if (this.statDateBefore != null) {
            predicates.add(cb.lessThan(root.get("statDate"), this.statDateBefore));
        }

        if (this.statDate != null) {
            predicates.add(cb.equal(root.get("statDate"), this.statDate));
        }

        if (this.userId != null) {
            Subquery<Merchant> sq = criteria.subquery(Merchant.class);
            Object sqRoot = sq.from(Merchant.class);
            List<Predicate> sqPredicates = new ArrayList();
            sqPredicates.add(cb.equal(((Root) sqRoot).get("id"), root.get("merchant").get("id")));
            sqPredicates.add(cb.equal(((Root) sqRoot).get("user").get("id"), this.userId));
            sq.select((Expression) sqRoot).where(cb.and((Predicate[]) sqPredicates.toArray(new Predicate[sqPredicates.size()])));
            predicates.add(cb.exists(sq));
        }
        return predicates;
    }

    public TypedQuery<BigDecimal> createAgentEarning(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteria = cb.createQuery(BigDecimal.class);
        Root<Bill> bill = criteria.from(Bill.class);
        criteria.select(cb.sum(bill.get("agentEarning")));
        List<Predicate> predicates = getPredicates(cb, criteria, bill);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<BigDecimal> query = em.createQuery(criteria);
        return query;
    }

    public TypedQuery<BigDecimal> createTicketEarning(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteria = cb.createQuery(BigDecimal.class);
        Root<Bill> bill = criteria.from(Bill.class);
        criteria.select(cb.sum(bill.get("ticketEarning")));
        List<Predicate> predicates = getPredicates(cb, criteria, bill);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<BigDecimal> query = em.createQuery(criteria);
        return query;
    }
}