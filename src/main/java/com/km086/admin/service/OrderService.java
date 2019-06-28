package com.km086.admin.service;

import java.util.List;

import com.km086.admin.model.account.Bill;
import com.km086.admin.model.order.Cart;
import com.km086.admin.model.order.CartFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

  List<Bill> createBill(CartFilter paramCartFilter);
  
  List<Cart> findCartByFilter(CartFilter paramCartFilter, Pageable paramPageable);
  
  Page<Cart> pageCartByFilter(CartFilter paramCartFilter, Pageable paramPageable);
}
