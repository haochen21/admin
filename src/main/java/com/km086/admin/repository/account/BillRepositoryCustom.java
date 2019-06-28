package com.km086.admin.repository.account;

import com.km086.admin.model.account.Bill;
import com.km086.admin.model.account.BillFilter;

import java.math.BigDecimal;
import java.util.List;

public interface BillRepositoryCustom {

    List<Bill> findByFilter(BillFilter paramBillFilter, Integer paramInteger1, Integer paramInteger2);

    Long countByFilter(BillFilter paramBillFilter);

    BigDecimal statAgentEarning(BillFilter paramBillFilter);

    BigDecimal statTicketEarning(BillFilter paramBillFilter);
}