package com.km086.admin.service;


import com.km086.admin.model.account.*;
import com.km086.admin.model.security.User;
import com.km086.admin.repository.account.AgentBillRepository;
import com.km086.admin.repository.account.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AccountServiceImpl implements AccountService {

    @Autowired
    BillRepository billRepository;

    @Autowired
    AgentBillRepository agentBillRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Bill saveBill(Bill bill) {
        return this.billRepository.save(bill);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Bill updateBillStatus(Long billId, BillStatus status) {
        Optional<Bill> optionalBill = this.billRepository.findById(billId);
        if (optionalBill.isPresent()) {
            optionalBill.get().setStatus(status);
            return optionalBill.get();
        } else {
            return null;
        }
    }

    public Bill findBillByMerhcantAndDate(Long merchantId, Date statDate) {
        return this.billRepository.findByMerhcantAndDate(merchantId, statDate);
    }

    public List<Bill> findBillByMerhcant(Long merchantId) {
        return this.billRepository.findByMerchant(merchantId);
    }

    public List<Bill> findBillByDate(Date statDate) {
        return this.billRepository.findByDate(statDate);
    }

    public List<Bill> findBillByFilter(BillFilter filter, Pageable pageable) {
        Integer startIndex = null;
        Integer pageSize = null;
        if (pageable != null) {
            startIndex = Integer.valueOf(pageable.getPageNumber());
            pageSize = Integer.valueOf(pageable.getPageSize());
        }
        return this.billRepository.findByFilter(filter, startIndex, pageSize);
    }

    public Page<Bill> pageBillByFilter(BillFilter filter, Pageable pageable) {
        Integer startIndex = null;
        Integer pageSize = null;
        if (pageable != null) {
            startIndex = Integer.valueOf(pageable.getPageNumber());
            pageSize = Integer.valueOf(pageable.getPageSize());
        }
        List<Bill> bills = this.billRepository.findByFilter(filter, startIndex, pageSize);
        Long count = this.billRepository.countByFilter(filter);
        Page<Bill> page = null;
        if (pageable == null) {
            page = new PageImpl(bills, PageRequest.of(0, Integer.parseInt("" + count)), count.longValue());
        } else {
            page = new PageImpl(bills, pageable, count.longValue());
        }
        return page;
    }

    public AgentBill createAgentBill(BillFilter filter, User agent) {
        BigDecimal earning = this.billRepository.statAgentEarning(filter);
        if (earning == null) {
            return null;
        }
        if (agent.getTransferOpenId() == null) {
            return null;
        }
        AgentBill agentBill = new AgentBill();
        BillNo billNo = new BillNo();
        agentBill.setNo(billNo.toHexString());
        agentBill.setName(agent.getName());
        agentBill.setOpenId(agent.getTransferOpenId());
        agentBill.setEarning(earning);
        agentBill.setStatBeginDate(filter.getStatDateAfter());
        agentBill.setStatEndDate(filter.getStatDateBefore());
        agentBill.setStatus(BillStatus.UNPAID);
        agentBill.setUser(agent);
        return agentBill;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public AgentBill updateAgentBillStatus(Long billId, BillStatus status) {
        Optional<AgentBill> optionalAgentBill = this.agentBillRepository.findById(billId);
        if (optionalAgentBill.isPresent()) {
            optionalAgentBill.get().setStatus(status);
            return optionalAgentBill.get();
        } else {
            return null;
        }
    }

    public AgentBill findAgentBillByAgentAndDate(Long userId, Date statBeginDate, Date statEndDate) {
        return this.agentBillRepository.findByAgentAndDate(userId, statBeginDate, statEndDate);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public AgentBill saveAgentBill(AgentBill agentBill) {
        return (AgentBill) this.agentBillRepository.save(agentBill);
    }

    public List<AgentBill> findAgentBillByFilter(AgentBillFilter filter, Pageable pageable) {
        Integer startIndex = null;
        Integer pageSize = null;
        if (pageable != null) {
            startIndex = Integer.valueOf(pageable.getPageNumber());
            pageSize = Integer.valueOf(pageable.getPageSize());
        }
        return this.agentBillRepository.findByFilter(filter, startIndex, pageSize);
    }

    public Page<AgentBill> pageAgentBillByFilter(AgentBillFilter filter, Pageable pageable) {
        Integer startIndex = null;
        Integer pageSize = null;
        if (pageable != null) {
            startIndex = Integer.valueOf(pageable.getPageNumber());
            pageSize = Integer.valueOf(pageable.getPageSize());
        }
        List<AgentBill> bills = this.agentBillRepository.findByFilter(filter, startIndex, pageSize);
        Long count = this.agentBillRepository.countByFilter(filter);
        Page<AgentBill> page = null;
        if (pageable == null) {
            page = new PageImpl(bills, PageRequest.of(0, Integer.parseInt("" + count)), count.longValue());
        } else {
            page = new PageImpl(bills, pageable, count.longValue());
        }
        return page;
    }

    public BigDecimal statTicketEarning(BillFilter filter) {
        return this.billRepository.statTicketEarning(filter);
    }
}