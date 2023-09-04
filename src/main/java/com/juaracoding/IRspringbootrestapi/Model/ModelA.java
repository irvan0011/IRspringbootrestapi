package com.juaracoding.IRspringbootrestapi.Model;


import javax.persistence.*;
import java.security.PrivateKey;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ModelA")
public class ModelA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDModelA")
    private Long IDModelA ;

    @Column(name = "CreatedBy",columnDefinition = "BIGINT NOT NULL default 1")
    private Date CreatedBy;

    @Column(name = "CreatedDate",columnDefinition = "DATETIME NOT NULL default GETDATE()")
    private Date CreatedDate;

    @Column(name = "IsActive",columnDefinition = "SMALLINT")
    private Integer IsActive;

    @Column(name = "ModelA",columnDefinition = "CHAR(20) NOT NULL default 'Default model A '")
    private  String ModelA;

    @Column(name = "ModifiedBy",columnDefinition = "BIGINT")
    private Long ModifiedBy;

    @Column(name = "ModifiedDate",columnDefinition = "DATETIME")
    private Date ModifiedDate;

    public Long getIDModelA() {
        return IDModelA;
    }

    public void setIDModelA(Long IDModelA) {
        this.IDModelA = IDModelA;
    }

    public Date getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(Date createdBy) {
        CreatedBy = createdBy;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public Integer getIsActive() {
        return IsActive;
    }

    public void setIsActive(Integer isActive) {
        IsActive = isActive;
    }

    public String getModelA() {
        return ModelA;
    }

    public void setModelA(String modelA) {
        ModelA = modelA;
    }

    public Long getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        ModifiedDate = modifiedDate;
    }
}
