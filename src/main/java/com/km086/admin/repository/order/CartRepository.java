package com.km086.admin.repository.order;

import com.km086.admin.model.order.Cart;
import com.km086.admin.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends BaseRepository<Cart, Long>, CartRepositoryCustom {

}
