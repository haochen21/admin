package com.km086.admin.model.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public enum NickNameEnCode {

    INSTANCE;


    public String encode(String str) {
        return new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)));
    }


    public String decode(String str) {
        byte[] encodedBytes = Base64.getDecoder().decode(str.getBytes());
        try {
            return new String(encodedBytes, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            return str;
        }
    }
}
