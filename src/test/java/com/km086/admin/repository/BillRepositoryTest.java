package com.km086.admin.repository;

import com.km086.admin.model.account.BillFilter;
import com.km086.admin.repository.account.BillRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillRepositoryTest {

    @Autowired
    BillRepository billRepository;

    @Test
    public void testCreateBill() throws Exception {
        BillFilter filter = new BillFilter();
        filter.setUserId(new Long(736));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        filter.setStatDateAfter(dateFormat.parse("2019-06-24 0:0:0"));
        filter.setStatDateBefore(dateFormat.parse("2019-07-01 0:0:0"));

        BigDecimal earning = billRepository.statAgentEarning(filter);
        System.out.println(earning);
    }
}
