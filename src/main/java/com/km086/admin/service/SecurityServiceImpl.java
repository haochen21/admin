package com.km086.admin.service;

import com.km086.admin.model.security.*;
import com.km086.admin.repository.security.DeviceRepository;
import com.km086.admin.repository.security.MerchantRepository;
import com.km086.admin.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User saveUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return (User) this.userRepository.save(user);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        Optional<User> optionalUserDbUser = this.userRepository.findById(user.getId());
        if (optionalUserDbUser.isPresent()) {
            optionalUserDbUser.get().setName(user.getName());
            optionalUserDbUser.get().setEmail(user.getEmail());
            optionalUserDbUser.get().setPhone(user.getPhone());
            optionalUserDbUser.get().setRate(user.getRate());
            optionalUserDbUser.get().setTransferOpenId(user.getTransferOpenId());
            if ((user.getPassword() != null) && (!user.getPassword().equals(""))) {
                optionalUserDbUser.get().setPassword(this.passwordEncoder.encode(user.getPassword()));
            }
        }
    }

    public User findUserById(Long userId) {
        return this.userRepository.findById(userId).orElse(null);
    }

    public List<User> findUserByName(String name) {
        return this.userRepository.findByName(name);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteUser(Long userId) {
        Optional<User> optionalUserDbUser = this.userRepository.findById(userId);
        if (optionalUserDbUser.isPresent()) {
            Collection<Merchant> merchants = optionalUserDbUser.get().getMerchants();
            for (Merchant m : merchants) {
                m.setUser(null);
            }
            this.userRepository.delete(optionalUserDbUser.get());
        }
    }

    public Boolean existsUserByLoginName(String loginName) {
        return this.userRepository.existsByLoginName(loginName);
    }

    public List<User> findUserByFilter(UserFilter filter, Pageable pageable) {
        Integer startIndex = null;
        Integer pageSize = null;
        if (pageable != null) {
            startIndex = Integer.valueOf(pageable.getPageNumber());
            pageSize = Integer.valueOf(pageable.getPageSize());
        }
        return this.userRepository.findByFilter(filter, startIndex, pageSize);
    }

    public Page<User> pageUserByFilter(UserFilter filter, Pageable pageable) {
        Integer startIndex = null;
        Integer pageSize = null;
        if (pageable != null) {
            startIndex = Integer.valueOf(pageable.getPageNumber());
            pageSize = Integer.valueOf(pageable.getPageSize());
        }
        List<User> users = this.userRepository.findByFilter(filter, startIndex, pageSize);
        Long count = this.userRepository.countByFilter(filter);
        Page<User> page = null;
        if (pageable == null) {
            page = new PageImpl(users, PageRequest.of(0, Integer.parseInt("" + count)), count.longValue());
        } else {
            page = new PageImpl(users, pageable, count.longValue());
        }
        return page;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void modifyUserPassword(Long id, String password) {
        Optional<User> optionalUserDbUser = this.userRepository.findById(id);
        if (optionalUserDbUser.isPresent()) {
            optionalUserDbUser.get().setPassword(this.passwordEncoder.encode(password));
            this.userRepository.save(optionalUserDbUser.get());
        }

    }

    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    public Merchant findMerchantById(Long id) {
        return this.merchantRepository.findById(id).get();
    }

    public Merchant findMerchantWithUser(Long id) {
        return this.merchantRepository.findByWithUser(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateMerchant(Merchant merchant) {
        Merchant dbMerchant = this.merchantRepository.findById(merchant.getId()).get();
        dbMerchant.setApproved(merchant.getApproved());
        dbMerchant.setAutoPayment(merchant.getAutoPayment());
        dbMerchant.setTakeOut(merchant.getTakeOut());
        dbMerchant.setDeviceNo(merchant.getDeviceNo());
        dbMerchant.setPrintNo(merchant.getPrintNo());
        dbMerchant.setDiscount(merchant.getDiscount());
        dbMerchant.setOpen(merchant.getOpen());
        dbMerchant.setRate(merchant.getRate());
        dbMerchant.setTakeByPhone(merchant.getTakeByPhone());
        dbMerchant.setTakeByPhoneSuffix(merchant.getTakeByPhoneSuffix());
        dbMerchant.setRealName(merchant.getRealName());
        dbMerchant.setTransferOpenId(merchant.getTransferOpenId());
        if (merchant.getUser() != null) {
            User user = (User) this.userRepository.findById(merchant.getUser().getId()).get();
            if (user != null) {
                dbMerchant.setUser(user);
            } else {
                dbMerchant.setUser(null);
            }
        } else {
            dbMerchant.setUser(null);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void trashMerchant(Long merchantId) {
        Merchant merchant = this.merchantRepository.findById(merchantId).get();

        if ((merchant.getDeviceNo() != null) && (!merchant.getDeviceNo().equals(""))) {
            Device device = this.deviceRepository.findByNo(merchant.getDeviceNo());
            if (device != null) {
                this.deviceRepository.delete(device);
            }
        }

        merchant.setDeviceNo(null);
        merchant.setTrash(Boolean.valueOf(true));
    }

    public List<Merchant> findMerchantNeedPayment() {
        return this.merchantRepository.findNeedPayment();
    }

    public List<Merchant> findMerchantByName(String name) {
        return this.merchantRepository.findByName(name);
    }

    public List<Merchant> findMerchantByFilter(MerchantFilter filter, Pageable pageable) {
        Integer startIndex = null;
        Integer pageSize = null;
        if (pageable != null) {
            startIndex = Integer.valueOf(pageable.getPageNumber());
            pageSize = Integer.valueOf(pageable.getPageSize());
        }
        return this.merchantRepository.findByFilter(filter, startIndex, pageSize);
    }

    public Page<Merchant> pageMerchantByFilter(MerchantFilter filter, Pageable pageable) {
        Integer startIndex = null;
        Integer pageSize = null;
        if (pageable != null) {
            startIndex = Integer.valueOf(pageable.getPageNumber());
            pageSize = Integer.valueOf(pageable.getPageSize());
        }
        List<Merchant> merchants = this.merchantRepository.findByFilter(filter, startIndex, pageSize);
        Long count = this.merchantRepository.countByFilter(filter);
        Page<Merchant> page = null;
        if (pageable == null) {
            page = new PageImpl(merchants, PageRequest.of(0, Integer.parseInt("" + count)), count.longValue());
        } else {
            page = new PageImpl(merchants, pageable, count.longValue());
        }
        return page;
    }
}
