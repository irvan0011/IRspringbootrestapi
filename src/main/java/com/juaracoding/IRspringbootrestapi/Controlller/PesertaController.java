package com.juaracoding.IRspringbootrestapi.Controlller;
import com.juaracoding.IRspringbootrestapi.Model.Address;
import com.juaracoding.IRspringbootrestapi.Model.Peserta;
import com.juaracoding.IRspringbootrestapi.repo.PesertaRepo;
import com.juaracoding.IRspringbootrestapi.service.PesertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/peserta") // memudahkan konfigurasi untuk mengakses method didalam class
public class PesertaController {

    //@Autowired membuat new object dapat menggunakan Autowired
    PesertaRepo pesertaRepo; //setiap object harus di instantiate ketika ingin di gunakan


    @Autowired
    PesertaService pesertaService;
    @Autowired
    public PesertaController(PesertaRepo pesertaRepo){

        /*
        untuk mempermudah membuat object core baru (yang akan kita gunakan seterusnya) dapat menggunakan
        constructor seperti ini sehingga hanya perlu melakukan sekali autowired
        */
        this.pesertaRepo=pesertaRepo;
    }
    @GetMapping("/getPeserta")
    public List<Peserta> getAllData() {
        List<Peserta> daftarPeserta = pesertaRepo.findAll();
        return daftarPeserta ;
    }

    @PostMapping("/postPeserta")
    public String postData(@RequestBody Peserta peserta) {
        //pesertaRepo.save(peserta);
        pesertaService.save(peserta);
        return peserta.getNama() + " berhasil ditambahkan";
    }


    @GetMapping("/search-peserta-by-id")
    public Map<String, Object> searchPesertaById(@RequestParam(value = "id") Integer id) {
        Optional<Peserta> hasil = pesertaRepo.findById(id); //optional itu seperti collection tapi hanya mengembalikan satu data
        Map<String,Object> m=new HashMap<>();
        if (hasil.isEmpty()){
            m.put("message","Data tidak ada");
            return  m;

        }
        m.put("hasil",hasil.get() );
        m.put("message","Data ditemukan");
        return m;
    }

    @GetMapping("/")
//    public List<Peserta> getAllData() {
    public ResponseEntity<Object> getAllDataResp() {
        Map<String,Object> m = new HashMap<String,Object>(); //menampilkan data respon request dan data tambahan yang akan kita kirim
        List<Peserta> daftarPeserta = pesertaRepo.findAll(); // memasukkan data respon ke dalam array
        m.put("data",daftarPeserta);
        m.put("waktu",new Date());
        m.put("message","Data ada !!");
        return new ResponseEntity<Object>(m, HttpStatus.ACCEPTED); // mengambil protokol status
    }
    @GetMapping("/search-peserta-by-nama")
    public List<Peserta> searchPesertaByNama(@RequestParam(value = "nama") String nama) {
        List<Peserta> hasil = pesertaRepo.findByNama(nama);
        return hasil;
    }
    @GetMapping("/search-peserta-by-batch")
    public List<Peserta> searchPesertaByBatch(@RequestParam(value = "batch") String batch) {
        List<Peserta> hasil = pesertaRepo.findByBatch(batch);
        return hasil;
    }
    @GetMapping("/search-4-peserta-by-batch-and-nama")
    public List<Peserta> searchPesertaByBatchAndNama(@RequestParam(value = "batch") String batch, @RequestParam(value = "nama") String nama) {
        List<Peserta> hasil = pesertaRepo.findTop4ByBatchAndNamaContaining(batch, nama);
        return hasil;
    }
    @GetMapping("/search-4-peserta-by-batch-nama")
    public List<Peserta> searchPesertaByBatchNama(@RequestParam(value = "batch") String batch, @RequestParam(value = "nama") String nama) {
        List<Peserta> hasil = pesertaRepo.findIRPesertaByBatchNama(batch, nama);
        return hasil;
    }
    @GetMapping("/search-peserta-by-nama-sw")
    public Map<String,Object> searchPesertaByNameSW(
            @RequestParam(value = "nama") String nama
    ) {
        List<Peserta> hasil = pesertaRepo.findByNamaStartsWith(nama);
        Map<String,Object> m = new HashMap<>();
        if(hasil.isEmpty())
        {
            m.put("message","Data tidak ada");
            return m;
        }
        m.put("data",hasil);
        m.put("message","Data ditemukan");
        return m;
    }
    @GetMapping("/search-peserta-by-nama-ew")
    public Map<String,Object> searchPesertaByNameEW(
            @RequestParam(value = "nama") String nama
    ) {
        List<Peserta> hasil = pesertaRepo.findByNamaEndsWith(nama);
        Map<String,Object> m = new HashMap<>();
        if(hasil.isEmpty())
        {
            m.put("message","Data tidak ada");
            return m;
        }
        m.put("data",hasil);
        m.put("message","Data ditemukan");
        return m;
    }

    @PostMapping("/delete-peserta-by-nama")
    public String deletePesertaByNama(@RequestParam(value = "nama") String nama) {
        pesertaRepo.deleteByNama(nama);
        return "Peserta bernama " + nama + " berhasil dihapus";
    }
    @GetMapping("/count-peserta-by-batch")
    public int countPesertaByBatch(@RequestParam(value = "batch") String batch) {
        int hasil = pesertaRepo.countByBatch(batch);
        return hasil;
    }



}
