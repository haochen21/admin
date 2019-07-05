package com.km086.admin.wx;

import java.math.BigDecimal;

public interface WxPayment {

    boolean payToWx(String billNo, String openId, BigDecimal payment);
}
