package com.juaracoding.IRspringbootrestapi.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.juaracoding.IRspringbootrestapi.dto.CartDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
    002
 */
@Entity
@Table(name = "MstBarang")
public class Barang implements Serializable {
    private static final Long serializeVersion = 70002L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDBarang")
    private Long idBarang;
    @Column(name = "NamaBarang")
    private String namaBarang;
    @Column(name = "Deskripsi",columnDefinition = "VARCHAR(50) default 'DEFAULT AJA BELUM DI SET' ",nullable = false)
    private String deskripsi ;
    @Column(name = "Img")
    private String img;
    @ManyToOne
    @JoinColumn(name = "IDKategoriBarangz", foreignKey = @ForeignKey(name = "fkBarangToKategori1"))
    private KategoriBarang kategoriBarang;

    @OneToMany(mappedBy = "idBarang")
    @JsonBackReference
    private List<Cart> listCart;
    @ManyToMany
    @JoinTable(name = "MapBarangSupplier",
            joinColumns  = {@JoinColumn(name = "IDBarang",referencedColumnName = "IDBarang",foreignKey = @ForeignKey(name = "fkMapToBarang"))},
            inverseJoinColumns = {@JoinColumn(name = "IDSupplier",referencedColumnName = "IDSupplier",foreignKey =@ForeignKey(name = "fkMapToSupplier"))},
            uniqueConstraints = @UniqueConstraint(columnNames = {"IDBarang","IDSupplier"})
    )
    private List<Supplier> listSupplier;

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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<Supplier> getListSupplier() {
        return listSupplier;
    }

    public void setListSupplier(List<Supplier> listSupplier) {
        this.listSupplier = listSupplier;
    }

    public Long getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(Long idBarang) {
        this.idBarang = idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public KategoriBarang getKategoriBarang() {
        return kategoriBarang;
    }

    public void setKategoriBarang(KategoriBarang kategoriBarang) {
        this.kategoriBarang = kategoriBarang;
    }
}