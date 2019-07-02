package com.km086.admin.wx;

import com.km086.admin.model.account.AgentBill;
import com.km086.admin.model.account.Bill;

public interface WxPayment {

    boolean payToMerchant(Bill paramBill);

    boolean payToAgent(AgentBill paramAgentBill);
}
