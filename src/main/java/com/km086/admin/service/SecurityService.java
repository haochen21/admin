package com.km086.admin.service;

import com.km086.admin.model.security.Merchant;
import com.km086.admin.model.security.MerchantFilter;
import com.km086.admin.model.security.User;
import com.km086.admin.model.security.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SecurityService {

  User saveUser(User paramUser);
  
  void updateUser(User paramUser);
  
  User findUserById(Long paramLong);
  
  void deleteUser(Long paramLong);
  
  Boolean existsUserByLoginName(String paramString);
  
  List<User> findUserByName(String paramString);
  
  List<User> findUserByFilter(UserFilter paramUserFilter, Pageable paramPageable);
  
  Page<User> pageUserByFilter(UserFilter paramUserFilter, Pageable paramPageable);
  
  void modifyUserPassword(Long paramLong, String paramString);
  
  Merchant findMerchantById(Long paramLong);
  
  Merchant findMerchantWithUser(Long paramLong);
  
  void updateMerchant(Merchant paramMerchant);
  
  void trashMerchant(Long paramLong);
  
  List<User> findAllUsers();
  
  List<Merchant> findMerchantNeedPayment();
  
  List<Merchant> findMerchantByName(String paramString);
  
  List<Merchant> findMerchantByFilter(MerchantFilter paramMerchantFilter, Pageable paramPageable);
  
  Page<Merchant> pageMerchantByFilter(MerchantFilter paramMerchantFilter, Pageable paramPageable);
}
