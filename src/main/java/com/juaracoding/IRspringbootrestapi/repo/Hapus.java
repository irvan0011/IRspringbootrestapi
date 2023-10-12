package com.juaracoding.IRspringbootrestapi.repo;

import com.juaracoding.IRspringbootrestapi.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;


public interface Hapus  extends JpaRepository<Barang,Long> {
    ResponseEntity<Object> deleteByNamaBarang(String s);
}
