package com.km086.admin.repository.security;


import com.km086.admin.model.security.Merchant;
import com.km086.admin.model.security.MerchantFilter;

import java.util.List;

public interface MerchantRepositoryCustom {

  List<Merchant> findByFilter(MerchantFilter paramMerchantFilter, Integer paramInteger1, Integer paramInteger2);
  
  Long countByFilter(MerchantFilter paramMerchantFilter);
}
