package com.juaracoding.IRspringbootrestapi.controller;

import com.juaracoding.IRspringbootrestapi.dto.KategoriBarangDTO;
import com.juaracoding.IRspringbootrestapi.model.KategoriBarang;
import com.juaracoding.IRspringbootrestapi.service.KategoriBarangService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/katbarang")
public class KategoriBarangController {

    @Autowired
    private ModelMapper modelMapper;
    private final static String MODUL_NAME = "KategoriBarang";
    private KategoriBarangService kategoriBarangService;

    public KategoriBarangController(KategoriBarangService kategoriBarangService) {
        this.kategoriBarangService = kategoriBarangService;
    }

    @PostMapping("/v1/sv")
    public ResponseEntity<Object> save(@Valid @RequestBody KategoriBarangDTO kategoriBarangDTO, HttpServletRequest request)
    {
        KategoriBarang kategoriBarang = modelMapper.map(kategoriBarangDTO, new TypeToken<KategoriBarang>() {}.getType());
        return kategoriBarangService.save(kategoriBarang,request);
    }

    @PutMapping("/v1/upd/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody KategoriBarangDTO kategoriBarangDTO, HttpServletRequest request)
            throws Exception
    {
        KategoriBarang kategoriBarang = modelMapper.map(kategoriBarangDTO, new TypeToken<KategoriBarang>() {}.getType());
        return kategoriBarangService.update(id,kategoriBarang,request);
    }

    @DeleteMapping("/v1/del/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return kategoriBarangService.delete(id,request);
    }

    @PostMapping("/v1/svb")
    public ResponseEntity<Object> saveBatch(@Valid @RequestBody List<KategoriBarangDTO> listKategoriBarangDTO,
                                            HttpServletRequest request)
    {
        List<KategoriBarang> listKategoriBarang = modelMapper.map(listKategoriBarangDTO, new TypeToken<List<KategoriBarang>>() {}.getType());
        return kategoriBarangService.saveBatch(listKategoriBarang,request);
    }

    @GetMapping("/v1/get/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return kategoriBarangService.findById(id,request);
    }

    @GetMapping("/v1/fbp/{page}/{size}")
    public ResponseEntity<Object> findByPage(
                                             @PathVariable(value = "page") Integer page,
                                             @PathVariable(value = "size") Integer size,
                                             @RequestParam(value = "col") String columFirst,
                                             @RequestParam(value = "val") String valueFirst,
                                             HttpServletRequest request)
    {
        return kategoriBarangService.findByPage(page,size,columFirst,valueFirst,request);
    }

    @GetMapping("/v1/fabp/{page}/{size}")
    public ResponseEntity<Object> findAllByPage(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size,
            HttpServletRequest request)
    {
        return kategoriBarangService.findAllByPage(page,size,request);
    }

    @GetMapping("/v1/findall")
    public ResponseEntity<Object> findAll(HttpServletRequest request)
    {
        return kategoriBarangService.findAll(request);
    }

    @PostMapping("/v1/uplcsv")
    public ResponseEntity<Object> export(@RequestParam("demoFile")
                                             @RequestHeader
                                             MultipartFile multipartFile,
                                             HttpServletRequest request)
    {
        return kategoriBarangService.dataToExport(multipartFile,request);
    }




}