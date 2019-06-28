package com.km086.admin.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserFilter implements Serializable {

    private Long id;

    private String name;

    private String loginName;

    private Profile profile;

    private int page;

    private int size;

    private static final long serialVersionUID = -3781522568411885697L;

    public TypedQuery<Long> createCountQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<User> user = criteria.from(User.class);
        criteria.select(cb.countDistinct(user.get("id")));
        List<Predicate> predicates = getPredicates(cb, criteria, user);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> query = em.createQuery(criteria);
        return query;
    }

    public TypedQuery<User> createListQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);
        criteria.select(user);
        criteria.distinct(true);

        List<Predicate> predicates = getPredicates(cb, criteria, user);
        criteria.where(cb.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<User> query = em.createQuery(criteria);
        return query;
    }

    private <T> List<Predicate> getPredicates(CriteriaBuilder cb, CriteriaQuery<T> criteria, Root<User> root) {
        List<Predicate> predicates = new ArrayList();

        if (this.id != null) {
            predicates.add(cb.equal(root.get("id"), this.id));
        }

        if ((this.name != null) && (!this.name.equals(""))) {
            predicates.add(cb.like(root.get("name"), "%" + this.name + "%"));
        }

        if ((this.loginName != null) && (!this.loginName.equals(""))) {
            predicates.add(cb.like(root.get("loginName"), "%" + this.loginName + "%"));
        }

        if (this.profile != null) {
            predicates.add(cb.equal(root.get("profile"), this.profile));
        }

        return predicates;
    }
}