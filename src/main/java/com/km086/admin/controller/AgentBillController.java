package com.km086.admin.controller;

import com.km086.admin.model.account.AgentBill;
import com.km086.admin.model.account.AgentBillFilter;
import com.km086.admin.security.AppUser;
import com.km086.admin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/api"})
public class AgentBillController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = {"/agentBill/page"}, method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public Page<AgentBill> pageAgentBillByFilter(@RequestBody AgentBillFilter filter) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!appUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            filter.setUserId(appUser.getId());
        }
        Page<AgentBill> page = this.accountService.pageAgentBillByFilter(filter, new PageRequest(filter
                .getPage(), filter.getSize()));
        return page;
    }

    @RequestMapping(value = {"/agentBill/list"}, method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public List<AgentBill> listAgentBillByFilter(@RequestBody AgentBillFilter filter) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!appUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            filter.setUserId(appUser.getId());
        }
        List<AgentBill> bills = this.accountService.findAgentBillByFilter(filter, new PageRequest(filter
                .getPage(), filter.getSize()));
        return bills;
    }
}