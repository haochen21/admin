package com.km086.admin.controller;

import com.km086.admin.model.order.Cart;
import com.km086.admin.model.order.CartFilter;
import com.km086.admin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api"})
public class CartController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = {"/order/page"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = {"application/json"}, produces = {"application/json"})
    public Page<Cart> pageCartByFilter(@RequestBody CartFilter filter) {
        Page<Cart> page = this.orderService.pageCartByFilter(filter, PageRequest.of(filter.getPage() - 1, filter.getSize()));
        return page;
    }
}

