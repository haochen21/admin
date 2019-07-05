package com.km086.admin.wx;

import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
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

    @Override
    public boolean payToWx(String billNo, String openId, BigDecimal payment) {
        try {
            BigDecimal amount = payment.setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            EntPayRequest request = EntPayRequest.newBuilder()
                    .partnerTradeNo(billNo)
                    .openid(openId)
                    .amount(amount.intValue())
                    .spbillCreateIp(wxPayProperties.getBillIp())
                    .checkName(WxPayConstants.CheckNameOption.NO_CHECK)
                    .description("转帐")
                    .build();
            EntPayResult entPayResult = wxPayService.getEntPayService().entPay(request);
            //log.info(entPayResult.toString());
            if ("SUCCESS".equalsIgnoreCase(entPayResult.getResultCode())
                    && "SUCCESS".equalsIgnoreCase(entPayResult.getReturnCode())) {
                return true;
            } else {
                return false;
            }
        } catch (WxPayException ex) {
            log.info("pay fail: {}", ex.getMessage());
            return false;
        }
    }

}