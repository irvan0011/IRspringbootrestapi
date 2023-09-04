package com.juaracoding.IRspringbootrestapi.Model;


import javax.persistence.*;
import java.security.PrivateKey;
import java.util.Date;

@Entity
@Table(name = "ModelB")
public class ModelB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDModelB")
    private Long IDModelB ;

    @Column(name = "CreatedBy",columnDefinition = "BIGINT NOT NULL default 1")
    private Date CreatedBy;

    @Column(name = "CreatedDate",columnDefinition = "DATETIME NOT NULL default GETDATE()")
    private Date CreatedDate;

    @Column(name = "IsActive",columnDefinition = "SMALLINT")
    private Integer IsActive;

    @Column(name = "ModelB",columnDefinition = "CHAR(20) NOT NULL default 'Default model B '")
    private  String ModelB;

    @Column(name = "ModifiedBy",columnDefinition = "BIGINT")
    private Long ModifiedBy;

    public Long getIDModelB() {
        return IDModelB;
    }

    public void setIDModelB(Long IDModelB) {
        this.IDModelB = IDModelB;
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

    public String getModelB() {
        return ModelB;
    }

    public void setModelB(String modelB) {
        ModelB = modelB;
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

    public ModelA getModelA() {
        return modelA;
    }

    public void setModelA(ModelA modelA) {
        this.modelA = modelA;
    }

    @Column(name = "ModifiedDate",columnDefinition = "DATETIME")
    private Date ModifiedDate;


    @ManyToOne
    @JoinColumn(name = "IDModelA",foreignKey = @ForeignKey(name = "fk_modelB_modelA",foreignKeyDefinition = "FOREIGN KEY ([IDModelA]) REFERENCES ModelA ([IDModelA])  ON DELETE SET NULL ON UPDATE SET NULL"))
    private ModelA modelA;


}
