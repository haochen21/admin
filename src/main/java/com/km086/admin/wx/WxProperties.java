package com.km086.admin.wx;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "wechat")
public class WxProperties {

    private String appId;

    private String secret;

    private String partnerId;

    private String partnerKey;

    private String token;

    private String aesKey;

    private String enterprisePayUrl;

    private String certificateLocation;

    private String spbillCreateIp;
}
