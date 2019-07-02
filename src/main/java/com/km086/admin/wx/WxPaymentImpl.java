package com.km086.admin.wx;

import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.km086.admin.model.account.AgentBill;
import com.km086.admin.model.account.Bill;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;



@Slf4j
@NoArgsConstructor
@Service
public class WxPaymentImpl implements WxPayment {

    @Autowired
    WxPayService wxPayService;

    @Autowired
    WxPayProperties wxPayProperties;

    public boolean payToMerchant(Bill bill) {
        try {
            BigDecimal amount = bill.getPayment().setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            EntPayRequest request = EntPayRequest.newBuilder()
                    .partnerTradeNo(bill.getNo())
                    .openid(bill.getOpenId())
                    .amount(amount.intValue())
                    .spbillCreateIp(wxPayProperties.getBillIp())
                    .checkName(WxPayConstants.CheckNameOption.NO_CHECK)
                    .description("转帐")
                    .build();

            this.log.info(wxPayService.getEntPayService().entPay(request).toString());
            return true;
        }catch(WxPayException ex){
            log.error("pay fail....",ex);
        }
        return false;
    }


    public boolean payToAgent(AgentBill agentBill) {
        try {
            BigDecimal amount = agentBill.getEarning().setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            EntPayRequest request = EntPayRequest.newBuilder()
                    .partnerTradeNo(agentBill.getNo())
                    .openid(agentBill.getOpenId())
                    .amount(amount.intValue())
                    .spbillCreateIp(wxPayProperties.getBillIp())
                    .checkName(WxPayConstants.CheckNameOption.NO_CHECK)
                    .description("转帐")
                    .build();

            this.log.info(wxPayService.getEntPayService().entPay(request).toString());
            return true;
        }catch(WxPayException ex){
            log.error("pay fail....",ex);
        }
        return false;
    }


}