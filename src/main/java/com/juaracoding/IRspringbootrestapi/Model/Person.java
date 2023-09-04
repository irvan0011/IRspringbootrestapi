package com.juaracoding.IRspringbootrestapi.Model;

import com.juaracoding.IRspringbootrestapi.util.ConstantPerson;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "IRPerson")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column(name = "nama",nullable = false,length = 25,unique = true)
    @Length(min = 5,max = 25,message = ConstantPerson.DATA_NOT_LENGTH)
    @NotNull(message = ConstantPerson.DATA_NOT_NULL)
    @NotEmpty(message = ConstantPerson.DATA_NOT_EMPTY)
    private  String nama;
    @Column(name = "jeniskelamin")
    private  String jenisKelamin;
    @Column(name = "umur")
    private String umur;

    @Column(name = "address",columnDefinition = "CHAR(50) default 'DEFAULT AJA BELUM DI SET' ",nullable = false)/*membuat default di database*/
    private String address;
    @Column(name = "CreatedDate",columnDefinition = "DATETIME NOT NULL default GETDATE()")
    private Date createdDate ;

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

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Peserta getPeserta() {
        return peserta;
    }

    public void setPeserta(Peserta peserta) {
        this.peserta = peserta;
    }

    @ManyToOne
    @JoinColumn(name = "peserta_id" /*NAMA KOLOM FOREIGN KEY*/, foreignKey = @ForeignKey(name = "fkPersonToPeserta"/*nama constraint yang akan di buat*/))
    private Peserta peserta;
}
