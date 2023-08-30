package com.juaracoding.IRspringbootrestapi.Controlller;
import com.juaracoding.IRspringbootrestapi.Model.Peserta;
import com.juaracoding.IRspringbootrestapi.repo.PesertaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class PesertaController {

    @Autowired
    PesertaRepo pesertaRepo;
    @GetMapping("/")
    public List<Peserta> getAllData() {
        List<Peserta> daftarPeserta = pesertaRepo.findAll();
        return daftarPeserta;
    }

    @PostMapping("/peserta")
    public String postData(@RequestBody Peserta peserta) {
        pesertaRepo.save(peserta);
        return peserta.getNama() + " berhasil ditambahkan";
    }

}
