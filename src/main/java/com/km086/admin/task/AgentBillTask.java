package com.km086.admin.task;

import com.km.admin.akka.actor.AgentSupervisorActor.CreateAgentBill;
import com.km.admin.model.security.Profile;
import com.km.admin.model.security.User;
import com.km.admin.model.security.UserFilter;
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
public class AgentBillTask {

    private static final Logger log = LoggerFactory.getLogger(MerchantBillTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    SecurityService securityService;
    @Autowired
    ActorSystem actorSystem;

    @Scheduled(cron = "${bill.agentCron}")
    public void bill() {
        log.info("agent bill starting......", dateFormat.format(new Date()));
        UserFilter userFilter = new UserFilter();
        userFilter.setProfile(Profile.AGENT);
        List<User> agents = this.securityService.findUserByFilter(userFilter, null);
        log.info("payment agent size is: " + agents.size());
        this.actorSystem.actorSelection("/user/supervisor/agentSupervisor").tell(new AgentSupervisorActor.CreateAgentBill(agents), ActorRef.noSender());
    }
}