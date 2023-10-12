package com.juaracoding.IRspringbootrestapi.repo;

import com.juaracoding.IRspringbootrestapi.dto.CartDTO;
import com.juaracoding.IRspringbootrestapi.model.Barang;
import com.juaracoding.IRspringbootrestapi.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Long> {

    public Page<Cart> findByidBarangContains(Pageable p, String val);
}
