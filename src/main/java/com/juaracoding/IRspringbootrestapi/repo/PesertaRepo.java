package com.juaracoding.IRspringbootrestapi.repo;
import com.juaracoding.IRspringbootrestapi.Model.Peserta;
import  org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PesertaRepo extends JpaRepository<Peserta /*Nama Model*/, Integer/*tipe data primary key*/> {
    /*
    * Penulisan JPA ada aturan penulisan sendiri
    * */
    List<Peserta> findByNama(String nama);
    List<Peserta> findByBatch(String batch);
    void deleteByNama(String nama);
    int countByBatch(String batch);

    List<Peserta> findTop4ByBatchAndNamaContaining(String batch, String nama); //Containing digunakan ketika kita ingin menggunakan query like=%..%
    List<Peserta> findByNamaStartsWith(String nama); //Statrswith digunakan ketika kita ingin menggunakan query like=%..
    List<Peserta> findByNamaEndsWith(String nama); //Statrswith digunakan ketika kita ingin menggunakan query like=%..
    @Query(value = "SELECT * FROM IRPeserta WHERE  batch = ?1 and nama = ?2", nativeQuery = true) // penggunaan native query
    List<Peserta> findIRPesertaByBatchNama(String batch, String nama); //find(aksi) IRPeserta(Nama table) BatchName(nama kolom)


}
