package com.juaracoding.IRspringbootrestapi.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Supplier")
public class Supplier implements Serializable {

    private static final Long serializeVersion = 70003L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSupplier")
    private Long idSupplier;

    @Column(name = "NamaSupplier")
    private String namaSupplier;

    @Column(name = "AlamatSupplier")
    private String alamatSupplier;

    @ManyToMany
    @JoinTable(name = "MapBarangSupplier" /*nama table baru untuk mapping join*/,
            joinColumns = {@JoinColumn(name = "IDSupplier" /*nama kolom yang akan di buat di table mapping join*/,referencedColumnName = "IDSupplier",foreignKey =@ForeignKey(name = "fkMapToSupplier"))},
            inverseJoinColumns  = {@JoinColumn(name = "IDBarang"  /*nama kolom yang akan di buat di table mapping join*/,referencedColumnName = "IDBarang",foreignKey = @ForeignKey(name = "fkMapToBarang"))},
            uniqueConstraints = @UniqueConstraint(columnNames = {"IDSupplier","IDBarang"})
    )
    private List<Barang> listBarang;


    public Long getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(Long idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    public String getAlamatSupplier() {
        return alamatSupplier;
    }

    public void setAlamatSupplier(String alamatSupplier) {
        this.alamatSupplier = alamatSupplier;
    }

    public List<Barang> getListBarang() {
        return listBarang;
    }

    public void setListBarang(List<Barang> listBarang) {
        this.listBarang = listBarang;
    }
}
