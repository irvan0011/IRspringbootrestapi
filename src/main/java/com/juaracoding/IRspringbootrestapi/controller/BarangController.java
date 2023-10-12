package com.juaracoding.IRspringbootrestapi.controller;

import com.juaracoding.IRspringbootrestapi.dto.BarangDTO;
import com.juaracoding.IRspringbootrestapi.dto.CreateBarangDTO;
import com.juaracoding.IRspringbootrestapi.handler.ResponseHandler;
import com.juaracoding.IRspringbootrestapi.model.Barang;
import com.juaracoding.IRspringbootrestapi.repo.BarangRepo;
import com.juaracoding.IRspringbootrestapi.service.BarangService;
import com.juaracoding.IRspringbootrestapi.service.UsrService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/barang")
public class BarangController {

    private final static String MODUL_NAME = "Barang";
    private BarangService barangService;
    private BarangRepo barangRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsrService usrService;
    @Autowired
    public BarangController(BarangService barangService) {
        this.barangService = barangService;
    }

    @PostMapping("/v1/sv")
    public ResponseEntity<Object> save(@RequestBody CreateBarangDTO createBarangDTO, HttpServletRequest request)
    {
        String token = request.getHeader("Authorization").substring(7);
        /*
            source untuk decode base64, yang merupakan payload dari JWT
         */
//        String encodeBase64Token = "eyJiYXJhbmciOiIxLTAtMSIsInN1YiI6InBvbGxjaGlodXkiLCJzdXBwbGllciI6IjExMSIsImthdGVnb3JpYmFyYW5nIjoiMDExIiwiZXhwIjoxNjk0MjY4MDA2LCJpYXQiOjE2OTQyNTAwMDZ9";
//        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodeBase64Token);
//        String decodedUrl = new String(decodedBytes);
//        System.out.println(decodedUrl);
//        Map <String,Object> map = usrService.checkAuthorization(token,MODUL_NAME,0,request);//index ke 0 di hardcode untuk urutan otorisasi nya
        Map <String,Object> map = usrService.checkAuthorization(token,MODUL_NAME,request);//menyamakan apakah modul sesuai dengan konfigurasi role nya...
        if(!(Boolean) map.get("isValid"))//ambil key isValid lalu di cast menjadi Boolean true = valid , false = tidak memiliki otorisasi terhadap API ini
        {
            return new ResponseHandler().generateResponse(
                    "Akses Ditolak",//message
                    HttpStatus.UNAUTHORIZED,//httpstatus
                    null,//object
                    "FV-Author001",
                    request
            );
        }
        Barang barang = modelMapper.map(createBarangDTO, new TypeToken<Barang>() {}.getType());
        return barangService.save(barang,request);
    }
    @PutMapping("/v1/upd/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody BarangDTO barangDTO, HttpServletRequest request)
            throws Exception
    {
        Barang barang = modelMapper.map(barangDTO, new TypeToken<Barang>() {}.getType());
        return barangService.update(id,barang,request);
    }

    @DeleteMapping("/v1/del/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return barangService.delete(id,request);
    }

    @PostMapping("/v1/svb")
    public ResponseEntity<Object> saveBatch(@Valid @RequestBody List<BarangDTO> listBarangDTO,
                                            HttpServletRequest request)
    {
        List<Barang> listBarang = modelMapper.map(listBarangDTO, new TypeToken<List<Barang>>() {}.getType());
        return barangService.saveBatch(listBarang,request);
    }

    @GetMapping("/v1/get/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return barangService.findById(id,request);
    }

    @GetMapping("/v1/fbp/{page}/{size}")
    public ResponseEntity<Object> findByPage(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size,
            @RequestParam(value = "col") String columFirst,
            @RequestParam(value = "val") String valueFirst,
            HttpServletRequest request)
    {
        return barangService.findByPage(page,size,columFirst,valueFirst,request);
    }

    @GetMapping("/v1/fabp/{page}/{size}")
    public ResponseEntity<Object> findAllByPage(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size,
            HttpServletRequest request)
    {
        return barangService.findAllByPage(page,size,request);
    }

    @GetMapping("/v1/findall")
    public ResponseEntity<Object> findAll(HttpServletRequest request)
    {
        return barangService.findAll(request);
    }

    @PostMapping("/v1/uplcsv")
    public ResponseEntity<Object> export(@RequestParam("demoFile")
                                         @RequestHeader
                                         MultipartFile multipartFile,
                                         HttpServletRequest request)
    {
        return barangService.dataToExport(multipartFile,request);
    }


}