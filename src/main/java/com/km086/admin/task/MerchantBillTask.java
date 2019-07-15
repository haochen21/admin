package com.km086.admin.task;

import com.km086.admin.model.account.Bill;
import com.km086.admin.model.account.BillStatus;
import com.km086.admin.model.order.CartFilter;
import com.km086.admin.model.security.Merchant;
import com.km086.admin.service.AccountService;
import com.km086.admin.service.MailService;
import com.km086.admin.service.OrderService;
import com.km086.admin.service.SecurityService;
import com.km086.admin.wx.WxPayment;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Component
public class MerchantBillTask {

    @Value("${bill.delayDay}")
    private int delayDay;

    @Value("#{'${ticketMail.to}'.split(',')}")
    String[] mailTos;

    @Autowired
    SecurityService securityService;

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @Autowired
    MailService mailService;

    @Autowired
    WxPayment wxPayment;

    private static final int delaySecond = 60;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Scheduled(cron = "${bill.cron}")
    public void bill() {
        log.info("bill starting......", dateFormat.format(new Date()));
        List<Merchant> merchants = this.securityService.findMerchantNeedPayment();
        log.info("payment merchant size is: " + merchants.size());

        List<Bill> transferBills = new ArrayList<>();
        for (Merchant merchant : merchants) {
            try {
                payBill(merchant, transferBills);
            } catch (Exception ex) {
                log.error("merchant create bill error!", ex);
            }
        }
        try {
            if (transferBills.size() > 0) {
                for (Bill transferBill : transferBills) {
                    boolean payResult = this.wxPayment.payToWx(transferBill.getNo(), transferBill.getOpenId(), transferBill.getPayment());
                    if (payResult) {
                        transferBill.setStatus(BillStatus.PAID);
                        this.accountService.updateBillStatus(transferBill.getId(), BillStatus.PAID);
                        log.info("merchant: {} pay bill: {} success ", transferBill.getMerchant().getId(), transferBill.getId());
                    } else {
                        log.info("merchant: {} pay bill: {} fail ", transferBill.getMerchant().getId(), transferBill.getId());
                    }
                    Thread.sleep(delaySecond);
                }
            }
            sendMail(transferBills);
        } catch (Exception ex) {
            log.error("merchant bill error!", ex);
        }
    }

    private void payBill(Merchant merchant, List<Bill> transferBills) {
        log.info("begin stat bill,merchant: {}", merchant.toString());

        CartFilter filter = new CartFilter();
        filter.setMerchant(merchant);
        filter.setWeixinPaid(true);

        LocalDateTime payTimeAfter = LocalDateTime.now();
        payTimeAfter = payTimeAfter.minusDays(this.delayDay);
        payTimeAfter = payTimeAfter.with(ChronoField.HOUR_OF_DAY, 0L);
        payTimeAfter = payTimeAfter.with(ChronoField.MINUTE_OF_HOUR, 0L);
        payTimeAfter = payTimeAfter.with(ChronoField.SECOND_OF_MINUTE, 0L);
        payTimeAfter = payTimeAfter.with(ChronoField.MILLI_OF_SECOND, 0L);
        ZonedDateTime payTimeAfterZdt = payTimeAfter.atZone(ZoneId.systemDefault());
        filter.setPayTimeAfter(Date.from(payTimeAfterZdt.toInstant()));

        LocalDateTime payTimeBefore = LocalDateTime.now();
        payTimeBefore = payTimeBefore.minusDays(1L);
        payTimeBefore = payTimeBefore.with(ChronoField.HOUR_OF_DAY, 23L);
        payTimeBefore = payTimeBefore.with(ChronoField.MINUTE_OF_HOUR, 59L);
        payTimeBefore = payTimeBefore.with(ChronoField.SECOND_OF_MINUTE, 59L);
        payTimeBefore = payTimeBefore.with(ChronoField.MILLI_OF_SECOND, 999L);
        ZonedDateTime payTimeBeforeZdt = payTimeBefore.atZone(ZoneId.systemDefault());
        filter.setPayTimeBefore(Date.from(payTimeBeforeZdt.toInstant()));

        List<Bill> bills = orderService.createBill(filter);
        for (final Bill bill : bills) {
            Bill dbBill = this.accountService.findBillByMerhcantAndDate(bill.getMerchant().getId(), bill.getStatDate());
            if (dbBill == null) {
                dbBill = this.accountService.saveBill(bill);
                transferBills.add(dbBill);
                log.info("add merchant: {} pay bill: {}", merchant.getId(), dbBill.getId());
            } else if (dbBill.getStatus() == BillStatus.UNPAID) {
                transferBills.add(dbBill);
                log.info("add merchant: {} unpaid bill: {}", merchant.getId(), dbBill.getId());
            }
        }
    }

    private void sendMail(List<Bill> bills) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<table border=\"1\">");
        sb.append("<thead><tr><th>商户名称</th><th>日期</th><th>销售金额</th><th>费率</th><th>手续费</th><th>实际转账</th><th>结果</th></tr></thead>");
        sb.append("<tbody>");
        for (final Bill bill : bills) {
            sb.append("<tr");
            if (bill.getStatus() == BillStatus.UNPAID) {
                sb.append(" bgcolor=\"red\"");
            }
            sb.append(">");
            sb.append("<td>").append(bill.getName()).append("</td>");
            sb.append("<td>").append(dateFormat.format(bill.getStatDate())).append("</td>");
            sb.append("<td>").append(this.round(bill.getTotalPrice())).append("</td>");
            sb.append("<td>").append(bill.getRate()).append("</td>");
            sb.append("<td>").append(this.round(bill.getServiceCharge())).append("</td>");
            sb.append("<td>").append(this.round(bill.getPayment())).append("</td>");
            sb.append("<td>").append(bill.getStatus().getDescription()).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("</table>");
        final String content = sb.toString();
        for (final String mailTo : this.mailTos) {
            this.mailService.sendSimpleMail(mailTo, dateFormat.format(new Date()) + " 商户账单", content);
        }
    }

    private double round(BigDecimal value) {
        return value.setScale(2, 4).doubleValue();
    }
}

