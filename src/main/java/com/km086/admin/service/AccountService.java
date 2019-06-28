package com.km086.admin.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.km086.admin.model.account.*;
import com.km086.admin.model.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

  Bill saveBill(Bill paramBill);
  
  Bill updateBillStatus(Long paramLong, BillStatus paramBillStatus);
  
  Bill findBillByMerhcantAndDate(Long paramLong, Date paramDate);
  
  List<Bill> findBillByMerhcant(Long paramLong);
  
  List<Bill> findBillByDate(Date paramDate);
  
  List<Bill> findBillByFilter(BillFilter paramBillFilter, Pageable paramPageable);
  
  Page<Bill> pageBillByFilter(BillFilter paramBillFilter, Pageable paramPageable);
  
  AgentBill createAgentBill(BillFilter paramBillFilter, User paramUser);
  
  AgentBill updateAgentBillStatus(Long paramLong, BillStatus paramBillStatus);
  
  AgentBill findAgentBillByAgentAndDate(Long paramLong, Date paramDate1, Date paramDate2);
  
  AgentBill saveAgentBill(AgentBill paramAgentBill);
  
  List<AgentBill> findAgentBillByFilter(AgentBillFilter paramAgentBillFilter, Pageable paramPageable);
  
  Page<AgentBill> pageAgentBillByFilter(AgentBillFilter paramAgentBillFilter, Pageable paramPageable);
  
  BigDecimal statTicketEarning(BillFilter paramBillFilter);
}
