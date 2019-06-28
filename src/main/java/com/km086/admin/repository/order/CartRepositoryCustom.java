package com.km086.admin.repository.order;

import com.km086.admin.model.order.Cart;
import com.km086.admin.model.order.CartFilter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CartRepositoryCustom {

    Map<Date, BigDecimal> statEarningByCreatedon(CartFilter paramCartFilter);

    List<Cart> findByFilter(CartFilter paramCartFilter, Integer paramInteger1, Integer paramInteger2);

    Long countByFilter(CartFilter paramCartFilter);
}