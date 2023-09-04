package com.juaracoding.IRspringbootrestapi.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/*
    70002
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
    @Column(name = "Deskripsi",columnDefinition = "CHAR(50)")
    private String deskripsi;
    @ManyToOne
    @JoinColumn(name = "IDKategoriBarang", foreignKey = @ForeignKey(name = "fkBarangToKategori"))
    private KategoriBarang kategoriBarang;

    @ManyToMany(mappedBy = "listBarang")/*diisi oleh nama variabel apa yang akan di ambil dari table ini ke table yang berelasi*/
    private List<Supplier> listSupplier;

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