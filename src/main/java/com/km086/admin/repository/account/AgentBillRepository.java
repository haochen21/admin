package com.km086.admin.repository.account;

import com.km086.admin.model.account.AgentBill;
import com.km086.admin.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AgentBillRepository extends BaseRepository<AgentBill, Long>, AgentBillRepositoryCustom{

  @Query("select b from AgentBill b where b.user.id = :userId and b.statBeginDate = :statBeginDate and b.statEndDate = :statEndDate")
  AgentBill findByAgentAndDate(@Param("userId") Long paramLong, @Param("statBeginDate") Date paramDate1, @Param("statEndDate") Date paramDate2);
}

