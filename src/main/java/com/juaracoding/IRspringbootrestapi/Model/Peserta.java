package com.juaracoding.IRspringbootrestapi.Model;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "IRPeserta")
public class Peserta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nama",nullable = false,length = 25)
    @Length(min = 5,max = 25,message = "Minimal")
    @NotNull(message = "Data tidak boleh NULL")
    @NotEmpty(message = "Data tidak boleh kosong")
    private String nama;

    @Column(name = "batch")
    private String batch;
    @Column(name = "alamat")
    private String alamat;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBatch() {
        return batch;
    }
    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
