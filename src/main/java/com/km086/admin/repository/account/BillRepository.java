package com.km086.admin.repository.account;

import com.km086.admin.model.account.Bill;
import com.km086.admin.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends BaseRepository<Bill, Long>, BillRepositoryCustom {

    @Query("select b from Bill b where b.merchant.id = :merchantId and b.statDate = :statDate")
    Bill findByMerhcantAndDate(@Param("merchantId") Long paramLong, @Param("statDate") Date paramDate);

    @Query("select b from Bill b where b.merchant.id = :merchantId")
    List<Bill> findByMerchant(@Param("merchantId") Long paramLong);

    @Query("select b from Bill b where b.statDate = :statDate")
    List<Bill> findByDate(@Param("statDate") Date paramDate);
}
