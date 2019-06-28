package com.km086.admin.task;

import com.km.admin.akka.actor.MerchantSupervisorActor.CreateMerchantBill;
import com.km.admin.model.security.Merchant;
import com.km.admin.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class MerchantBillTask {
    private static final Logger log = LoggerFactory.getLogger(MerchantBillTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    SecurityService securityService;
    @Autowired
    ActorSystem actorSystem;

    @Scheduled(cron = "${bill.cron}")
    public void bill() {
        log.info("bill starting......", dateFormat.format(new Date()));
        List<Merchant> merchants = this.securityService.findMerchantNeedPayment();
        log.info("payment merchant size is: " + merchants.size());
        this.actorSystem.actorSelection("/user/supervisor/merchantSupervisor").tell(new MerchantSupervisorActor.CreateMerchantBill(merchants), ActorRef.noSender());
    }
}

