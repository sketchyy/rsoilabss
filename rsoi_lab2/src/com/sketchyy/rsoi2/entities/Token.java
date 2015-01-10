package com.sketchyy.rsoi2.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sketchy on 18.12.2014.
 */
@Entity
@Table(name = "RSOI2_TOKENS", schema = "SKETCHYY", catalog = "")
public class Token {
    private int tokenId;
    private int userId;
    private String token;
    private Timestamp expiresIn;

    public Token() {
    }

    public Token(int userId, String token, Timestamp expiresIn) {
        this.userId = userId;
        this.token = token;
        this.expiresIn = expiresIn;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokenSeq")
    @SequenceGenerator(name="tokenSeq", sequenceName="RSOI2_TOKENS_SEQ", allocationSize=1)
    @Column(name = "TOKEN_ID")
    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
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
    @Column(name = "TOKEN")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "EXPIRES_IN")
    public Timestamp getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Timestamp expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token1 = (Token) o;

        if (tokenId != token1.tokenId) return false;
        if (userId != token1.userId) return false;
        if (expiresIn != null ? !expiresIn.equals(token1.expiresIn) : token1.expiresIn != null) return false;
        if (token != null ? !token.equals(token1.token) : token1.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tokenId;
        result = 31 * result + userId;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (expiresIn != null ? expiresIn.hashCode() : 0);
        return result;
    }
}
