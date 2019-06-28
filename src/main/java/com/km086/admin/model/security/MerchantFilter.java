package com.km086.admin.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class MerchantFilter implements Serializable {

    private String deviceNo;

    private String openId;

    private String transferOpenId;

    private String name;

    private String loginName;

    private Boolean open;

    private Boolean autoPayment;

    private Boolean trash;

    private Long userId;

    private int page;

    private int size;

    private static final long serialVersionUID = -3781522568411885697L;

    public TypedQuery<Long> createCountQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<Merchant> merchant = criteria.from(Merchant.class);
        criteria.select(cb.countDistinct(merchant.get("id")));
        List<Predicate> predicates = getPredicates(cb, criteria, merchant);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> query = em.createQuery(criteria);
        return query;
    }

    public TypedQuery<Merchant> createListQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Merchant> criteria = cb.createQuery(Merchant.class);
        Root<Merchant> merchant = criteria.from(Merchant.class);
        merchant.fetch("user", JoinType.LEFT);
        criteria.select(merchant);
        criteria.distinct(true);
        criteria.orderBy(new Order[]{cb.asc(merchant.get("id"))});
        List<Predicate> predicates = getPredicates(cb, criteria, merchant);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Merchant> query = em.createQuery(criteria);
        return query;
    }

    private <T> List<Predicate> getPredicates(CriteriaBuilder cb, CriteriaQuery<T> criteria, Root<Merchant> root) {
        List<Predicate> predicates = new ArrayList();

        if ((this.deviceNo != null) && (!this.deviceNo.equals(""))) {
            predicates.add(cb.like(root.get("deviceNo"), "%" + this.deviceNo + "%"));
        }

        if ((this.openId != null) && (!this.openId.equals(""))) {
            predicates.add(cb.like(root.get("openId"), "%" + this.openId + "%"));
        }

        if ((this.transferOpenId != null) && (!this.transferOpenId.equals(""))) {
            predicates.add(cb.like(root.get("transferOpenId"), "%" + this.transferOpenId + "%"));
        }

        if ((this.name != null) && (!this.name.equals(""))) {
            predicates.add(cb.like(root.get("name"), "%" + this.name + "%"));
        }

        if ((this.loginName != null) && (!this.loginName.equals(""))) {
            predicates.add(cb.like(root.get("loginName"), "%" + this.loginName + "%"));
        }

        if (this.open != null) {
            predicates.add(cb.equal(root.get("open"), this.open));
        }

        if (this.autoPayment != null) {
            predicates.add(cb.equal(root.get("autoPayment"), this.autoPayment));
        }

        if (this.userId != null) {
            predicates.add(cb.equal(root.get("user").get("id"), this.userId));
        }

        if (this.trash != null) {
            if (this.trash.booleanValue()) {
                predicates.add(cb.equal(root.get("trash"), this.trash));
            } else {
                predicates.add(cb.or(cb.equal(root.get("trash"), this.trash), cb.isNull(root.get("trash"))));
            }
        }

        return predicates;
    }
}