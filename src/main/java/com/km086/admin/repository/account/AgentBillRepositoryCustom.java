package com.km086.admin.repository.account;

import com.km086.admin.model.account.AgentBill;
import com.km086.admin.model.account.AgentBillFilter;

import java.util.List;

public interface AgentBillRepositoryCustom {

    List<AgentBill> findByFilter(AgentBillFilter paramAgentBillFilter, Integer paramInteger1, Integer paramInteger2);

    Long countByFilter(AgentBillFilter paramAgentBillFilter);
}