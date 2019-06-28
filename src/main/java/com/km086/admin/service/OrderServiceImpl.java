package com.km086.admin.service;

import com.km086.admin.model.account.Bill;
import com.km086.admin.model.account.BillNo;
import com.km086.admin.model.account.BillStatus;
import com.km086.admin.model.order.Cart;
import com.km086.admin.model.order.CartFilter;
import com.km086.admin.model.security.User;
import com.km086.admin.repository.order.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartRepository cartRepository;

    public List<Bill> createBill(CartFilter filter) {
        List<Bill> bills = new ArrayList();
        Map<Date, BigDecimal> statBills = this.cartRepository.statEarningByCreatedon(filter);
        for (Map.Entry<Date, BigDecimal> entry : statBills.entrySet()) {
            Date key = entry.getKey();
            BigDecimal value = entry.getValue();

            Bill bill = new Bill();
            BillNo billNo = new BillNo();
            bill.setNo(billNo.toHexString());
            bill.setMerchant(filter.getMerchant());
            bill.setName(filter.getMerchant().getName());
            bill.setOpenId(filter.getMerchant().getTransferOpenId());
            bill.setStatDate(key);
            bill.setTotalPrice(value);
            bill.setRate(filter.getMerchant().getRate());
            bill.setServiceCharge(value.multiply(new BigDecimal(filter.getMerchant().getRate().floatValue())));
            bill.setPayment(value.subtract(bill.getServiceCharge()));
            if (filter.getMerchant().getUser() != null) {
                User user = filter.getMerchant().getUser();
                if (user.getRate() != null) {
                    bill.setAgentRate(user.getRate());
                } else {
                    bill.setAgentRate(new Float(0.0D));
                }
                bill.setAgentEarning(value.multiply(new BigDecimal(bill.getAgentRate().floatValue())));
                bill.setUser(user);
            } else {
                bill.setAgentRate(new Float(0.0D));
                bill.setAgentEarning(new BigDecimal(0.0D));
            }
            bill.setWeixinEarning(value.multiply(new BigDecimal(0.006D)));

            bill.setTicketEarning(bill
                    .getServiceCharge().subtract(bill.getWeixinEarning()).subtract(bill.getAgentEarning()));

            bill.setStatus(BillStatus.UNPAID);

            bills.add(bill);
        }
        return bills;
    }

    public List<Cart> findCartByFilter(CartFilter filter, Pageable pageable) {
        Integer startIndex = null;
        Integer pageSize = null;
        if (pageable != null) {
            startIndex = Integer.valueOf(pageable.getPageNumber());
            pageSize = Integer.valueOf(pageable.getPageSize());
        }
        return this.cartRepository.findByFilter(filter, startIndex, pageSize);
    }

    public Page<Cart> pageCartByFilter(CartFilter filter, Pageable pageable) {
        Integer startIndex = null;
        Integer pageSize = null;
        if (pageable != null) {
            startIndex = Integer.valueOf(pageable.getPageNumber());
            pageSize = Integer.valueOf(pageable.getPageSize());
        }
        List<Cart> carts = this.cartRepository.findByFilter(filter, startIndex, pageSize);
        Long count = this.cartRepository.countByFilter(filter);
        Page<Cart> page = null;
        if (pageable == null) {
            page = new PageImpl(carts, PageRequest.of(0, Integer.parseInt("" + count)), count.longValue());
        } else {
            page = new PageImpl(carts, pageable, count.longValue());
        }
        return page;
    }
}
