package com.eliasshallouf.examples.simple_reservation_system.domain.model.request;

import java.io.Serializable;

public class LoginModel implements Serializable {
    private String email;
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
