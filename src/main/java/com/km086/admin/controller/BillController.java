package com.km086.admin.controller;

import com.km086.admin.model.account.Bill;
import com.km086.admin.model.account.BillFilter;
import com.km086.admin.security.AppUser;
import com.km086.admin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping({"/api"})
public class BillController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = {"/bill/page"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = {"application/json"}, produces = {"application/json"})
    public Page<Bill> pageBillByFilter(@RequestBody BillFilter filter) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!appUser.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            filter.setUserId(appUser.getId());
        }
        Page<Bill> page = this.accountService.pageBillByFilter(filter, PageRequest.of(filter.getPage() - 1, filter.getSize()));
        return page;
    }

    @RequestMapping(value = {"/bill/statTicketEarning"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST}, consumes = {"application/json"})
    public Float statCartNumber(@RequestBody BillFilter filter) {
        BigDecimal earning = this.accountService.statTicketEarning(filter);
        if (earning == null) {
            return new Float(0.0D);
        }
        return Float.valueOf(earning.setScale(2, RoundingMode.HALF_UP).floatValue());
    }
}
