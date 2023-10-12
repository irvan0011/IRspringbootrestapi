package com.juaracoding.IRspringbootrestapi.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

public class UsrDTO {


    private Long idUser;

    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^\\w{8,15}$",message = "Format user tidak boleh spasi (minimal 8 maksimal 15)")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$",message = "Format user tidak boleh spasi (minimal 8 maksimal 15)")
    private String userName;

    @NotNull
    @NotEmpty
    @NotBlank
    //    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}:;<>,.?/~_+-=|]).{10,20}$",message = "Kombinasi Huruf,Angka dan spesial character !!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{10,20}$",message = "Minimal 1 simbol & alfanumeric , ex : Paul@123... Kombinasi Huruf dan Angka !!(Password Minimal 10 Maksimal 20 Karakter)")
//    @Pattern(regexp = "",message = "Minimal 1 simbol & alfanumeric , ex : Paul@123...")//MINIMAL 1 SIMBOL & ALFANUMERIK
    private String password;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",message = "ex : poll.chihuy@gmail.com")//RFC532
    private String email;


    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^((((62)|0))8)([0-9]{8,12})$",message = "ex : 628 atau 08 12345678 ")//((62 | 0 )8)([0-9].{8,12})
    private String noHp;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 10,max = 60)
    private String namaLengkap;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min=40,max = 255)
    private String alamat;
    private String token;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Pattern(regexp = "^(19|(2[0-9]))([0-9][0-9])-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$")
    private Date tanggalLahir;
    private Integer umur;


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }



    public void setUmur(Integer umur) {
        this.umur = umur;
    }
}
