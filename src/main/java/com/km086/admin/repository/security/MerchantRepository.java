package com.km086.admin.repository.security;

import com.km086.admin.model.security.Merchant;
import com.km086.admin.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantRepository extends BaseRepository<Merchant, Long>, MerchantRepositoryCustom {

    @Query("select m from Merchant m LEFT JOIN FETCH m.user where m.autoPayment = true and m.rate is not null and m.transferOpenId is not null")
    List<Merchant> findNeedPayment();

    @Query("select m from Merchant m where m.name like %:name%")
    List<Merchant> findByName(@Param("name") String paramString);

    @Query("select m from Merchant m LEFT JOIN FETCH m.user where m.id = :id")
    Merchant findByWithUser(@Param("id") Long paramLong);
}

