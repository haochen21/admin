package com.km086.admin.security;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final boolean isAdmin;

    private final String token;

    public JwtAuthenticationResponse(String token,boolean isAdmin) {
        this.token = token;
        this.isAdmin = isAdmin;
    }

    public String getToken() {
        return this.token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
