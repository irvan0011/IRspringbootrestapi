package com.juaracoding.IRspringbootrestapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
    002
 */
@Entity
@Table(name = "TrxBarangKeluar")
public class BarangKeluar implements Serializable {

    private static final Long serializeVersion = 70002L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDBarangKeluar")
    private Long idBarangKeluar;
    @Column(name = "NomorTrx")
    private Integer nomorTrx;
    @Column(name = "TglKeluar")
    private Date tglKeluar = new Date();
    @ManyToOne
    @JoinColumn(name = "IDSupplierz", foreignKey = @ForeignKey(name = "fkBarangKeluarToSupplier"))
    private Supplier supplier ;
    @OneToMany(mappedBy = "barangKeluar")
    private List<Item> listItem ;

    @Column(name = "CreatedBy",columnDefinition = "BIGINT NOT NULL default 1")
    private Long createdBy = 1L;

    @Column(name = "CreatedDate",columnDefinition = "DATETIME NOT NULL default GETDATE()")
    private Date createdDate=new Date();

    @Column(name = "ModifiedBy")
    private Long modifiedBy;

    @Column(name = "ModifiedDate",columnDefinition = "DATETIME NULL")
    private Date modifiedDate;

    @Column(name = "IsActive")
    private Byte isActive=1;
}
