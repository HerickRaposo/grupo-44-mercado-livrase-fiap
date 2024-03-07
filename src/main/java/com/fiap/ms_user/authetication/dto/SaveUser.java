package com.fiap.ms_user.authetication.dto;

import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class SaveUser implements Serializable {

    @Size(min=4)
    private String name;
    private String username_or_email;

    @Size(min=11)
    private String cpf;
    @Size(min=8)
    private String password;
    @Size(min=8)
    private String repeatedPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername_or_email() {
        return username_or_email;
    }

    public void setUsername_or_email(String username_or_email) {
        this.username_or_email = username_or_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
