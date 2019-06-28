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

import java.util.List;

@RestController
@RequestMapping({"/api"})
public class CartController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = {"/cart/page"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = {"application/json"}, produces = {"application/json"})
    public Page<Cart> pageCartByFilter(@RequestBody CartFilter filter) {
        Page<Cart> page = this.orderService.pageCartByFilter(filter, new PageRequest(filter.getPage(), filter.getSize()));
        return page;
    }

    @RequestMapping(value = {"/cart/list"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = {"application/json"}, produces = {"application/json"})
    public List<Cart> listCartByFilter(@RequestBody CartFilter filter) {
        List<Cart> carts = this.orderService.findCartByFilter(filter, new PageRequest(filter.getPage(), filter.getSize()));
        return carts;
    }
}

