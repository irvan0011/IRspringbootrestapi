package com.juaracoding.IRspringbootrestapi.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "TrxItem")
public class Item implements Serializable {

    private static final Long serializeVersion = 70002L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDItem")
    private Long idBarangKeluar;
    @Column(name = "NomorTrx")
    private Integer nomorTrx;
    @ManyToOne
    @JoinColumn(name = "IDBarangz", foreignKey = @ForeignKey(name = "fkItemToBarang"))
    private Barang barang ;
    @Column(name = "Qty",nullable = false)
    private Integer qty=1;
    @Column(name = "Harga",nullable = false)
    private Long harga;
    @ManyToOne
    @JoinColumn(name = "IDBarangKeluarz", foreignKey = @ForeignKey(name = "fkItemToBarangKeluar"))
    private BarangKeluar barangKeluar;

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
