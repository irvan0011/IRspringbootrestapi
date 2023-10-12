package com.juaracoding.IRspringbootrestapi.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.juaracoding.IRspringbootrestapi.model.Barang;
import com.juaracoding.IRspringbootrestapi.model.Usr;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.time.LocalDate;
import  java.time.LocalDate;

public class CartDTO {


    private Long idCart;


    @NotNull(message = "Field Nama Barang Tidak Boleh Null")
    @NotBlank(message = "Field Nama Barang Tidak Boleh Blank")
    @NotEmpty(message = "Field Nama Barang Tidak Boleh Kosong")
    private BarangDTO idBarang;
    private Integer qty;
    @NotNull(message = "Field Nama Barang Tidak Boleh Null")
    @NotBlank(message = "Field Nama Barang Tidak Boleh Blank")
    @NotEmpty(message = "Field Nama Barang Tidak Boleh Kosong")
    private UsrDTO idUser ;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate = new Date();

    public Long getIdCart() {
        return idCart;
    }

    public void setIdCart(Long idCart) {
        this.idCart = idCart;
    }

    public BarangDTO getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(BarangDTO idBarang) {
        this.idBarang = idBarang;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public UsrDTO getIdUser() {
        return idUser;
    }

    public void setIdUser(UsrDTO idUser) {
        this.idUser = idUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
