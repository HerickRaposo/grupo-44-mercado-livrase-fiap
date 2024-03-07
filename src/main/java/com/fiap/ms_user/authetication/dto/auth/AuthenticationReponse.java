package com.fiap.ms_user.authetication.dto.auth;

import java.io.Serializable;

public class AuthenticationReponse implements Serializable {

    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
