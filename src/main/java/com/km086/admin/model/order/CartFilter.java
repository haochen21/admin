package com.km086.admin.model.order;

import com.km086.admin.model.security.Merchant;
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
public class CartFilter implements Serializable {

    private Merchant merchant;

    private Collection<CartStatus> statuses;

    private Date payTimeBefore;

    private Date payTimeAfter;

    private Date createOnBefore;

    private Date createOnAfter;

    private Boolean weixinPaid = Boolean.valueOf(true);

    private int page;

    private int size;

    private static final long serialVersionUID = -7728555630999945069L;


    private <T> List<Predicate> getPredicates(CriteriaBuilder cb, CriteriaQuery<T> criteria, Root<Cart> root) {
        List<Predicate> predicates = new ArrayList();

        if (this.merchant != null) {
            predicates.add(cb.equal(root.get("merchant").get("id"), this.merchant.getId()));
        }

        if (this.weixinPaid.booleanValue()) {
            predicates.add(cb.isNotNull(root.get("transactionId")));
        }

        if (this.statuses != null) {
            if (this.statuses.size() > 1) {
                CriteriaBuilder.In<CartStatus> statusPredicate = cb.in(root.get("status"));
                for (CartStatus status : this.statuses) {
                    statusPredicate.value(status);
                }
                predicates.add(statusPredicate);
            } else {
                predicates.add(cb.equal(root.get("status"), this.statuses.iterator().next()));
            }
        }

        if ((this.payTimeAfter != null) && (this.payTimeBefore != null)) {
            predicates.add(cb.between(root.get("payTime"), this.payTimeAfter, this.payTimeBefore));
        } else if (this.payTimeAfter != null) {
            predicates.add(cb.greaterThan(root.get("payTime"), this.payTimeAfter));
        } else if (this.payTimeBefore != null) {
            predicates.add(cb.lessThan(root.get("payTime"), this.payTimeBefore));
        }

        if ((this.createOnAfter != null) && (this.createOnBefore != null)) {
            predicates.add(cb.between(root.get("createdOn"), this.createOnAfter, this.createOnBefore));
        } else if (this.createOnAfter != null) {
            predicates.add(cb.greaterThan(root.get("createdOn"), this.createOnAfter));
        } else if (this.createOnBefore != null) {
            predicates.add(cb.lessThan(root.get("createdOn"), this.createOnBefore));
        }

        return predicates;
    }

    public TypedQuery<Long> createCountQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<Cart> cart = criteria.from(Cart.class);
        criteria.select(cb.countDistinct(cart.get("id")));
        List<Predicate> predicates = getPredicates(cb, criteria, cart);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> query = em.createQuery(criteria);
        return query;
    }

    public TypedQuery<Cart> createListQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cart> criteria = cb.createQuery(Cart.class);
        Root<Cart> cart = criteria.from(Cart.class);
        cart.fetch("merchant", JoinType.INNER);
        cart.fetch("customer", JoinType.INNER);
        cart.fetch("cartItems", JoinType.LEFT);
        criteria.select(cart);
        criteria.distinct(true);

        List<Predicate> predicates = getPredicates(cb, criteria, cart);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));

        criteria.orderBy(new Order[]{cb.desc(cart.get("createdOn"))});

        TypedQuery<Cart> query = em.createQuery(criteria);

        return query;
    }

    public TypedQuery<Object[]> createEarningByPayTime(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = cb.createQuery(Object[].class);
        Root<Cart> cart = criteria.from(Cart.class);
        Expression<String> dateFunction = cb.function("DATE_FORMAT", String.class, new Expression[]{cart.get("payTime"), cb
                .literal("%Y-%m-%d")});
        criteria.multiselect(new Selection[]{dateFunction.alias("statDate"), cb.sum(cart.get("totalPrice"))});
        List<Predicate> predicates = getPredicates(cb, criteria, cart);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        criteria.groupBy(new Expression[]{dateFunction});
        TypedQuery<Object[]> query = em.createQuery(criteria);
        return query;
    }
}
