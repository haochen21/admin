package com.km086.admin.controller;

import com.km086.admin.model.security.Merchant;
import com.km086.admin.model.security.MerchantFilter;
import com.km086.admin.security.AppUser;
import com.km086.admin.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api"})
public class MerchantController {

    @Autowired
    private SecurityService securityService;

    private static final Logger log = LoggerFactory.getLogger(MerchantController.class);

    @RequestMapping(value = {"/merchant/name/{name}"}, method = RequestMethod.GET, produces = {"application/json"})
    public List<Merchant> findMechantByName(@PathVariable String name) {
        MerchantFilter filter = new MerchantFilter();
        filter.setName(name);

        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!appUser.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            filter.setUserId(appUser.getId());
        }
        List<Merchant> merchants = this.securityService.findMerchantByFilter(filter, null);
        return merchants;
    }

    @RequestMapping(value = {"/merchant/page"}, method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public Page<Merchant> pageBillByFilter(@RequestBody MerchantFilter filter) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!appUser.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            filter.setUserId(appUser.getId());
        }
        Page<Merchant> page = this.securityService.pageMerchantByFilter(filter, PageRequest.of(filter
                .getPage() - 1, filter.getSize()));
        return page;
    }

    @RequestMapping(value = {"/merchant/{id}"}, method = RequestMethod.GET, produces = {"application/json"})
    public Merchant findMerchantById(@PathVariable Long id) {
        Merchant merchant = this.securityService.findMerchantWithUser(id);
        return merchant;
    }

    @RequestMapping(value = {"/merchant/{id}"}, method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    public Merchant modifyMerchant(@RequestBody Merchant merchant) {
        log.info("modify merchant: " + merchant.toString());
        return this.securityService.updateMerchant(merchant);
    }

    @RequestMapping(value = {"/merchant/trash/{id}"}, method = RequestMethod.PUT)
    public void trashMerchant(@PathVariable Long id) {
        log.info("trash merchant: " + id);
        this.securityService.trashMerchant(id);
    }
}
