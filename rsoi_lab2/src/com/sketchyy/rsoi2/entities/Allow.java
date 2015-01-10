package com.sketchyy.rsoi2.entities;

import javax.persistence.*;

/**
 * Created by Sketchy on 21.12.2014.
 */
@Entity
@Table(name = "RSOI2_ALLOWS", schema = "SKETCHYY", catalog = "")
public class Allow {
    private int allowId;
    private int userId;
    private String clientId;
    private String isAllowed;

    public Allow(int userId, String client, String allow) {
        this.userId = userId;
        this.clientId = client;
        this.isAllowed = allow;
    }

    public Allow() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allowSeq")
    @SequenceGenerator(name="allowSeq", sequenceName="RSOI2_ALLOWS_SEQ", allocationSize=1)
    @Column(name = "ALLOW_ID")
    public int getAllowId() {
        return allowId;
    }

    public void setAllowId(int allowId) {
        this.allowId = allowId;
    }

    @Basic
    @Column(name = "USER_ID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "CLIENT_ID")
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "IS_ALLOWED")
    public String getIsAllowed() {
        return isAllowed;
    }

    public void setIsAllowed(String isAllowed) {
        this.isAllowed = isAllowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Allow allow = (Allow) o;

        if (allowId != allow.allowId) return false;
        if (userId != allow.userId) return false;
        if (clientId != null ? !clientId.equals(allow.clientId) : allow.clientId != null) return false;
        if (isAllowed != null ? !isAllowed.equals(allow.isAllowed) : allow.isAllowed != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = allowId;
        result = 31 * result + userId;
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (isAllowed != null ? isAllowed.hashCode() : 0);
        return result;
    }
}
