package com.juaracoding.IRspringbootrestapi.Model;


import javax.persistence.*;
import java.security.PrivateKey;
import java.util.Date;

@Entity
@Table(name = "ModelX")

public class TugasA {

    @Id
    @Column(name = "IDModelX",columnDefinition = "VARCHAR(30)")
    private String IDModelX;

    @Column(name = "Alamat",columnDefinition = "NVARCHAR(255) NOT NULL default 'BELUM DIISI'")
    private String Alamat;

    @Column(name = "CreatedBy",columnDefinition = "BIGINT NOT NULL default 0")
    private Long CreatedBy;

    @Column(name = "CreatedDate",columnDefinition = "DATETIME NOT NULL default GETDATE()")
    private Date CreatedDate;

    @Column(name = "IsActive",columnDefinition = "SMALLINT ")
    private Integer IsActive;

    @Column(name = "JenisKelamin",columnDefinition = "CHAR(1) default 'p'")
    private  String JenisKelamin;

    @Column(name = "MasihHidup",columnDefinition = "BIT")
    private String MasihHidup;

    @Column(name = "ModifiedBy",columnDefinition = "BIGINT")
    private Long ModifiedBy;

    @Column(name = "ModifiedDate",columnDefinition = "DATETIME")
    private Date ModifiedDate;

    @Column(name = "Nama",columnDefinition = "CHAR(40) NOT NULL default 'SEDANG DIMINTA'")
    private String Nama;

    @Column(name = "TanggalLahir",columnDefinition = "DATE")
    private Date TanggalLahir;

}
