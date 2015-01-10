package com.sketchyy.rsoi2.entities;

import javax.persistence.*;

/**
 * Created by Sketchy on 18.12.2014.
 */
@Entity
@Table(name = "RSOI2_APPS", schema = "SKETCHYY", catalog = "")
public class App {
    private String clientId;
    private String secret;

    @Id
    @Column(name = "CLIENT_ID")
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "SECRET")
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        App app = (App) o;

        if (clientId != null ? !clientId.equals(app.clientId) : app.clientId != null) return false;
        if (secret != null ? !secret.equals(app.secret) : app.secret != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clientId != null ? clientId.hashCode() : 0;
        result = 31 * result + (secret != null ? secret.hashCode() : 0);
        return result;
    }
}
