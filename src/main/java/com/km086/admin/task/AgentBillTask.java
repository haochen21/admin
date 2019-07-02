package com.km086.admin.task;

import com.km086.admin.model.account.AgentBill;
import com.km086.admin.model.account.BillFilter;
import com.km086.admin.model.account.BillStatus;
import com.km086.admin.model.security.Profile;
import com.km086.admin.model.security.User;
import com.km086.admin.model.security.UserFilter;
import com.km086.admin.service.AccountService;
import com.km086.admin.service.MailService;
import com.km086.admin.service.SecurityService;
import com.km086.admin.wx.WxPayment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class AgentBillTask {

    @Value("#{'${ticketMail.to}'.split(',')}")
    String[] mailTos;

    @Autowired
    AccountService accountService;

    @Autowired
    WxPayment wxPayment;

    @Autowired
    MailService mailService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    SecurityService securityService;

    @Scheduled(cron = "${bill.agentCron}")
    public void bill() {
        log.info("agent bill starting......", dateFormat.format(new Date()));
        UserFilter userFilter = new UserFilter();
        userFilter.setProfile(Profile.AGENT);
        List<User> agents = this.securityService.findUserByFilter(userFilter, null);
        log.info("payment agent size is: " + agents.size());
    }

    private void payBill(User agent) {
        log.info(agent.getName() + " begin stat bill");
        BillFilter billFilter = new BillFilter();
        billFilter.setUserId(agent.getId());

        ZoneId zone = ZoneId.systemDefault();
        LocalDate startCalendar = LocalDate.now();
        startCalendar = startCalendar.with(DayOfWeek.MONDAY);
        startCalendar = startCalendar.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        Instant startInstant = startCalendar.atStartOfDay().atZone(zone).toInstant();
        billFilter.setStatDateAfter(Date.from(startInstant));

        LocalDate endCalendar = LocalDate.now();
        endCalendar = endCalendar.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        Instant endInstant = endCalendar.atStartOfDay().atZone(zone).toInstant();
        billFilter.setStatDateBefore(Date.from(endInstant));

        AgentBill agentBill = this.accountService.createAgentBill(billFilter, agent);

        if (agentBill != null) {
            AgentBill dbAgentBill = this.accountService.findAgentBillByAgentAndDate(agent.getId(), agentBill
                    .getStatBeginDate(), agentBill.getStatEndDate());
            if (dbAgentBill == null) {
                dbAgentBill = this.accountService.saveAgentBill(agentBill);
            }

            if (dbAgentBill.getStatus() != BillStatus.PAID) {
                boolean payResult = this.wxPayment.payToAgent(agentBill);
                if (payResult) {
                    this.accountService.updateAgentBillStatus(agentBill.getId(), BillStatus.PAID);
                }
            }
        }
    }

    private void sendMail(List<AgentBill> bills) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<table border=\"1\">");
        sb.append("<thead><tr><th>代理商</th><th>开始日期</th><th>结束日期</th><th>金额</th><th>结果</th></tr></thead>");
        sb.append("<tbody>");
        for (AgentBill agentBill : bills) {
            sb.append("<tr");
            if (agentBill.getStatus() == BillStatus.UNPAID) {
                sb.append(" bgcolor=\"red\"");
            }
            sb.append(">");
            sb.append("<td>").append(agentBill.getName()).append("</td>");
            sb.append("<td>").append(dateFormat.format(agentBill.getStatBeginDate())).append("</td>");
            sb.append("<td>").append(dateFormat.format(agentBill.getStatEndDate())).append("</td>");
            sb.append("<td>").append(this.round(agentBill.getEarning())).append("</td>");
            sb.append("<td>").append(agentBill.getStatus().getDescription()).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("</table>");
        final String content = sb.toString();
        for (String mailTo : this.mailTos) {
            this.mailService.sendSimpleMail(mailTo, dateFormat.format(new Date()) + " 代理商账单", content);
        }
    }

    private double round(BigDecimal value) {
        return value.setScale(2, 4).doubleValue();
    }
}