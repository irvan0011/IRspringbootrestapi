package com.juaracoding.IRspringbootrestapi.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.juaracoding.IRspringbootrestapi.model.Barang;

import javax.persistence.*;
import java.util.List;

public class SupplierDTO {
    private Long idSupplier;
    private String namaSupplier;

    private String alamatSupplier;
    @JsonBackReference
    private List<BarangDTO> listBarang;
    private List<CreateBarangDTO> listCreateBarang;

    public List<CreateBarangDTO> getListCreateBarang() {
        return listCreateBarang;
    }

    public void setListCreateBarang(List<CreateBarangDTO> listCreateBarang) {
        this.listCreateBarang = listCreateBarang;
    }

    private Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    private  Long modifiedBy;

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

    public List<BarangDTO> getListBarang() {
        return listBarang;
    }

    public void setListBarang(List<BarangDTO> listBarang) {
        this.listBarang = listBarang;
    }
}
