package com.km.admin.wx;

import com.km086.admin.model.account.AgentBill;
import com.km086.admin.model.account.Bill;
import com.km086.admin.wx.WxPayment;
import com.km086.admin.wx.WxProperties;
import com.km086.admin.wx.util.Sha1Util;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.KeyStore;
import java.util.*;


@Slf4j
@Service
public class WxPaymentImpl implements WxPayment {
    
    @Autowired
    protected WxMpConfigStorage wxMpConfigStorage;
    
    @Autowired
    private WxProperties properties;

    public boolean payToMerchant(Bill bill) {
        TreeMap<String, String> map = new TreeMap();
        map.put("mch_appid", this.wxMpConfigStorage.getAppId());
        map.put("mchid", this.wxMpConfigStorage.getPartnerId());
        map.put("nonce_str", Sha1Util.getNonceStr());
        map.put("partner_trade_no", bill.getNo());
        map.put("openid", bill.getOpenId());
        map.put("check_name", "NO_CHECK");
        BigDecimal amount = bill.getPayment().setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        map.put("amount", "" + amount.longValue());
        map.put("desc", "转帐");
        map.put("spbill_create_ip", this.properties.getSpbillCreateIp());
        try {
            Map<String, String> returnMap = enterprisePay(map, this.wxMpConfigStorage.getPartnerKey(), this.properties
                    .getCertificateLocation(), this.properties.getEnterprisePayUrl());
            if (("SUCCESS".equals(((String) returnMap.get("result_code")).toUpperCase())) &&
                    ("SUCCESS".equals(((String) returnMap.get("return_code")).toUpperCase()))) {
                log.info("企业对个人付款成功！\n付款信息：\n" + returnMap.toString() + ",帐单:" + bill.toString());
                return true;
            }
            log.error("err_code: " +
                    (String) returnMap.get("err_code") + "  err_code_des: " + (String) returnMap.get("err_code_des"));
            return false;
        } catch (Exception ex) {
            log.info("pay fail....", ex);
        }
        return false;
    }


    public boolean payToAgent(AgentBill agentBill) {
        TreeMap<String, String> map = new TreeMap();
        map.put("mch_appid", this.wxMpConfigStorage.getAppId());
        map.put("mchid", this.wxMpConfigStorage.getPartnerId());
        map.put("nonce_str", Sha1Util.getNonceStr());
        map.put("partner_trade_no", agentBill.getNo());
        map.put("openid", agentBill.getOpenId());
                                                      
        map.put("check_name", "NO_CHECK");
                                                      
        BigDecimal amount = agentBill.getEarning().setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                                                      
        map.put("amount", "" + amount.longValue());
                                                      
        map.put("desc", "转帐");
                                                      
        map.put("spbill_create_ip", this.properties.getSpbillCreateIp());
        try {
                                                          
            Map<String, String> returnMap = enterprisePay(map, this.wxMpConfigStorage.getPartnerKey(), this.properties
.getCertificateLocation(), this.properties.getEnterprisePayUrl());
                                                          
            if (("SUCCESS".equals(((String) returnMap.get("result_code")).toUpperCase())) &&
                                                                           ("SUCCESS".equals(((String) returnMap.get("return_code")).toUpperCase()))) {
                                                             
                log.info("企业对个人付款成功！\n付款信息：\n" + returnMap.toString() + ",帐单:" + agentBill.toString());
                                                             
                return true;
            }
                                                         
            log.error("err_code: " +
                                                                          (String) returnMap.get("err_code") + "  err_code_des: " + (String) returnMap.get("err_code_des"));
                                                         
            return false;
        } catch (Exception ex) {
            log.info("pay fail....", ex);
        }
                                                     
        return false;
    }


    public boolean fakePayToMerchant(Bill bill) {
                                                     
        Random rand = new Random();
                                                     
        if (rand.nextBoolean()) {
                                                         
            return true;
        }
                                                     
        return false;
    }


    public boolean fakePayToAgent(AgentBill bill) {
                                                     
        Random rand = new Random();
                                                     
        if (rand.nextBoolean()) {
                                                         
            return true;
        }
                                                     
        return false;
    }


    public Map<String, String> enterprisePay(Map<String, String> map, String keys, String paths, String uri)
            throws Exception {
                                                     
        String mchId = (String) map.get("mchid");
                                                     
        Set<Map.Entry<String, String>> entry2 = map.entrySet();
                                                     
        StringBuilder sb = new StringBuilder();
                                                     
        for (Map.Entry<String, String> obj : entry2) {
                                                         
            k = (String) obj.getKey();
                                                         
            String v = (String) obj.getValue();
                                                         
            if ((null != v) && (!"".equals(v))) {
                                                             
                sb.append(k).append('=').append(v).append('&');
            }
        }
        String k;
                                                     
        sb.append("key=").append(keys);
                                                     
        String str2 = MD5Util.md5Encode(sb.toString(), "utf-8").toUpperCase();
                                                     
        map.put("sign", str2);
                                                     
        StringBuilder builder = new StringBuilder();
                                                     
        builder.append("<xml>");
                                                     
        for (Map.Entry<String, String> entry : map.entrySet()) {
                                                         
            builder.append('<').append((String) entry.getKey()).append('>').append((String) entry.getValue()).append("</").append((String) entry.getKey()).append('>');
        }
                                                     
        builder.append("</xml>");
                                                     
        String desc = new String(builder.toString().getBytes("UTF-8"), "ISO-8859-1");
                                                     
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
                                                     
        FileInputStream instream = new FileInputStream(new File(paths));
        Throwable localThrowable6 = null;
                                                     
        try {
            keyStore.load(instream, mchId.toCharArray());
        } catch (Throwable localThrowable1) {
                                                         
            localThrowable6 = localThrowable1;
            throw localThrowable1;
        } finally {
                                                         
            if (instream != null) if (localThrowable6 != null) try {
                instream.close();
            } catch (Throwable localThrowable2) {
                localThrowable6.addSuppressed(localThrowable2);
            }
            else instream.close();
        }
                                                     
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
                                                     
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);


                                                     
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        localThrowable2 = null;
                                                     
        try {
            HttpPost httpPost = new HttpPost(uri);
                                                         
            StringEntity str = new StringEntity(desc);
                                                         
            httpPost.setEntity(str);
                                                         
            returnMap = getMap(httpclient, httpPost);
        } catch (Throwable localThrowable4) {
            Map<String, String> returnMap;
                                                         
            localThrowable2 = localThrowable4;
            throw localThrowable4;

        } finally {
                                                         
            if (httpclient != null) if (localThrowable2 != null) try {
                httpclient.close();
            } catch (Throwable localThrowable5) {
                localThrowable2.addSuppressed(localThrowable5);
            }
            else httpclient.close();
        }
                                                     
        Map<String, String> returnMap;
        return returnMap;
    }

    private Map<String, String> getMap(CloseableHttpClient httpclient, HttpPost httpPost) throws Exception {
                                                     
        CloseableHttpResponse response = httpclient.execute(httpPost);
        Throwable localThrowable3 = null;
                                                     
        try {
            returnMap = getReturnMap(response);
        } catch (Throwable localThrowable1) {
            Map<String, String> returnMap;
                                                         
            localThrowable3 = localThrowable1;
            throw localThrowable1;
        } finally {
                                                         
            if (response != null) if (localThrowable3 != null) try {
                response.close();
            } catch (Throwable localThrowable2) {
                localThrowable3.addSuppressed(localThrowable2);
            }
            else response.close();
        }
                                                     
        Map<String, String> returnMap;
        return returnMap;
    }

    private Map<String, String> getReturnMap(CloseableHttpResponse response) throws Exception {
                                                     
        Map<String, String> returnMap = new HashMap();
                                                     
        HttpEntity entity = response.getEntity();
                                                     
        if (entity != null) {
                                                         
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
            Throwable localThrowable3 = null;
                                                         
            try {
                String text = bufferedReader.readLine();
                                                             
                StringBuilder result = new StringBuilder();
                                                             
                while (null != text) {
                                                                 
                    result.append(text);
                                                                 
                    text = bufferedReader.readLine();
                }

                                                             
                returnMap = XMLUtil.parseXmlStringToMap(new String(result.toString().getBytes("utf-8"), "utf-8"));
            } catch (Throwable localThrowable1) {
                                                             
                localThrowable3 = localThrowable1;
                throw localThrowable1;


            } finally {



                                                              
                if (bufferedReader != null) if (localThrowable3 != null) try {
                    bufferedReader.close();
                } catch (Throwable localThrowable2) {
                    localThrowable3.addSuppressed(localThrowable2);
                }
                else bufferedReader.close();
            }
        }
                                                      
        EntityUtils.consume(entity);
                                                      
        return returnMap;
    }
}