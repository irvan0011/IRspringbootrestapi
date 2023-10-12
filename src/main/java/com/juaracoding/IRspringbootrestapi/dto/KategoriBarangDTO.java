package com.juaracoding.IRspringbootrestapi.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class KategoriBarangDTO {
    private Long idKategoriBarang;
    @NotNull(message = "Field Nama Kategori Tidak Boleh Null")
    @NotBlank(message = "Field Nama Kategori Tidak Boleh Blank")
    @Length(min = 5, max = 25,message = "Field Nama Kategori Minimal 5 Maksimal 12")
    @NotEmpty(message = "Field Nama Kategori Tidak Boleh Kosong")
    private String namaKategoriBarang;

    private String messageToUser;

    @JsonBackReference
    List<BarangDTO> listBarang;

    public List<BarangDTO> getListBarang() {
        return listBarang;
    }

    public void setListBarang(List<BarangDTO> listBarang) {
        this.listBarang = listBarang;
    }

    public String getMessageToUser() {
        return messageToUser;
    }

    public void setMessageToUser(String messageToUser) {
        this.messageToUser = messageToUser;
    }

    public Long getIdKategoriBarang() {
        return idKategoriBarang;
    }

    public void setIdKategoriBarang(Long idKategoriBarang) {
        this.idKategoriBarang = idKategoriBarang;
    }

    public String getNamaKategoriBarang() {
        return namaKategoriBarang;
    }

    public void setNamaKategoriBarang(String namaKategoriBarang) {
        this.namaKategoriBarang = namaKategoriBarang;
    }
}
