package com.sketchyy.rsoi2.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sketchy on 18.12.2014.
 */
@Entity
@Table(name = "RSOI2_CODES", schema = "SKETCHYY", catalog = "")
public class Code {
    private int codeId;
    private int userId;
    private String code;
    private Timestamp expiresIn;

    public Code() {
    }

    public Code(int userId, String code, Timestamp expiresIn) {
        this.userId = userId;
        this.code = code;
        this.expiresIn = expiresIn;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codeSeq")
    @SequenceGenerator(name="codeSeq", sequenceName="RSOI2_APPS_SEQ", allocationSize=1)
    @Column(name = "CODE_ID")
    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
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
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

        Code code1 = (Code) o;

        if (codeId != code1.codeId) return false;
        if (userId != code1.userId) return false;
        if (code != null ? !code.equals(code1.code) : code1.code != null) return false;
        if (expiresIn != null ? !expiresIn.equals(code1.expiresIn) : code1.expiresIn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codeId;
        result = 31 * result + userId;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (expiresIn != null ? expiresIn.hashCode() : 0);
        return result;
    }
}
