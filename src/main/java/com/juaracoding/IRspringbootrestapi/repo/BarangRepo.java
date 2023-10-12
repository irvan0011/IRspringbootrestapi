package com.juaracoding.IRspringbootrestapi.repo;

import com.juaracoding.IRspringbootrestapi.model.Barang;
import com.juaracoding.IRspringbootrestapi.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public interface BarangRepo  extends JpaRepository<Barang,Long> {

    public Page<Barang> findByNamaBarangContains(Pageable p, String val);
    public Page<Barang> findByDeskripsiContains(Pageable p, String val);
    @Query(value = "INSERT INTO [dbo].[MapBarangSupplier] (IDSupplier,IDBarang) values (?1,?2)", nativeQuery = true)

    public Long saveBarang(Long brg, Long supp);//001-010
}
