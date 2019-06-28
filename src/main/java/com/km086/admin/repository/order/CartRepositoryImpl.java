package com.km086.admin.repository.order;

import com.km086.admin.model.order.Cart;
import com.km086.admin.model.order.CartFilter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class CartRepositoryImpl  implements CartRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    public List<Cart> findByFilter(CartFilter filter, Integer startIndex, Integer pageSize) {
        List<Cart> carts = new ArrayList();

        TypedQuery<Cart> query = filter.createListQuery(this.em);
        if ((startIndex != null) && (pageSize != null)) {
            query.setFirstResult(startIndex.intValue() * pageSize.intValue());
            query.setMaxResults(pageSize.intValue());
        }
        List<Cart> dbCarts = query.getResultList();
        if ((dbCarts != null) && (dbCarts.size() > 0)) {
            carts.addAll(dbCarts);
        }
        return carts;
    }

    public Long countByFilter(CartFilter filter) {
        TypedQuery<Long> query = filter.createCountQuery(this.em);
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public Map<Date, BigDecimal> statEarningByCreatedon(CartFilter filter) {
        Map<Date, BigDecimal> stats = new HashMap();
        TypedQuery<Object[]> query = filter.createEarningByPayTime(this.em);
        List<Object[]> result = query.getResultList();
        for (Object[] row : result) {
            try {
                String dateValue = row[0].toString();
                BigDecimal price = BigDecimal.valueOf(((Number) row[1]).doubleValue());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                stats.put(sdf.parse(dateValue), price);
            } catch (Exception ex) {
                log.info("statEarningByCreatedon:", ex);
            }
        }
        return stats;
    }
}