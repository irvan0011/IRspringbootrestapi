package com.juaracoding.IRspringbootrestapi.controller;

import com.juaracoding.IRspringbootrestapi.configuration.OtherConfiguration;
import com.juaracoding.IRspringbootrestapi.core.IService;
import com.juaracoding.IRspringbootrestapi.dto.BarangDTO;
import com.juaracoding.IRspringbootrestapi.handler.RequestCapture;
import com.juaracoding.IRspringbootrestapi.handler.ResponseHandler;
import com.juaracoding.IRspringbootrestapi.model.BarangKeluar;
import com.juaracoding.IRspringbootrestapi.repo.BarangKeluarRepo;
import com.juaracoding.IRspringbootrestapi.repo.ItemRepo;
import com.juaracoding.IRspringbootrestapi.repo.LogRequestRepo;
import com.juaracoding.IRspringbootrestapi.service.LogService;
import com.juaracoding.IRspringbootrestapi.service.UsrService;
import com.juaracoding.IRspringbootrestapi.util.LogTable;
import com.juaracoding.IRspringbootrestapi.util.LoggingFile;
import com.juaracoding.IRspringbootrestapi.util.TransformDataPaging;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/barangkeluar")
public class BarangKeluarController  implements IService<BarangKeluar> {

    private BarangKeluarRepo barangKeluarRepo;
    private ItemRepo itemRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsrService usrService;

    private String [] strExceptionArr = new String[2];
    private TransformDataPaging transformDataPaging = new TransformDataPaging();
    private Map<String,Object> mapz = new HashMap<>();
    @Autowired
    private LogRequestRepo logRequestRepo;

    @Autowired
    private LogService logService;

    @Autowired
    public BarangKeluarController(BarangKeluarRepo barangKeluarRepo) {
        strExceptionArr[0] = "BarangKeluarController";
        this.barangKeluarRepo = barangKeluarRepo;
    }

    @PostMapping("/v1/sv")
    public ResponseEntity<Object> save(@RequestBody BarangKeluar barangKeluar, HttpServletRequest request){
        if(barangKeluar==null)
        {
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    null,//object
                    "FV002001",//errorCode Fail Validation modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        try{
            BarangKeluar keluar= modelMapper.map(barangKeluar, new TypeToken<BarangKeluar>() {}.getType());
            barangKeluarRepo.save(keluar);


        }catch (Exception e)
        {
            strExceptionArr[1] = "save(Barang barangKeluar, HttpServletRequest request) --- LINE 59 \n"+ RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002001",//errorCode Fail Error modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Data Berhasil Disimpan",//message
                HttpStatus.CREATED,//httpstatus created
                null,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> update(Long id, BarangKeluar barangKeluar, HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> saveBatch(List<BarangKeluar> lt, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByPage(Integer page, Integer size, String columFirst, String valueFirst, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAllByPage(Integer page, Integer size, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> dataToExport(MultipartFile multipartFile, HttpServletRequest request) {
        return null;
    }
}
