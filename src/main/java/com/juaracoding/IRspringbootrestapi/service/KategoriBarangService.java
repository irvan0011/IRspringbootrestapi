package com.juaracoding.IRspringbootrestapi.service;

import com.juaracoding.IRspringbootrestapi.configuration.OtherConfiguration;
import com.juaracoding.IRspringbootrestapi.core.IService;
import com.juaracoding.IRspringbootrestapi.dto.KategoriBarangDTO;
import com.juaracoding.IRspringbootrestapi.handler.RequestCapture;
import com.juaracoding.IRspringbootrestapi.handler.ResponseHandler;
import com.juaracoding.IRspringbootrestapi.model.KategoriBarang;
import com.juaracoding.IRspringbootrestapi.repo.KategoriBarangRepo;
import com.juaracoding.IRspringbootrestapi.repo.LogRequestRepo;
import com.juaracoding.IRspringbootrestapi.util.CsvReader;
import com.juaracoding.IRspringbootrestapi.util.LogTable;
import com.juaracoding.IRspringbootrestapi.util.LoggingFile;
import com.juaracoding.IRspringbootrestapi.util.TransformDataPaging;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class KategoriBarangService  implements IService<KategoriBarang>{

    private KategoriBarangRepo kategoriBarangRepo;
    private String [] strExceptionArr = new String[2];
    private TransformDataPaging transformDataPaging = new TransformDataPaging();
    private Map<String,Object> mapz = new HashMap<>();
    @Autowired
    private LogRequestRepo logRequestRepo;
    private ModelMapper modelMapper;

    @Autowired
    public KategoriBarangService(KategoriBarangRepo kategoriBarangRepo, ModelMapper modelMapper) {
        strExceptionArr[0] = "KategoriBarangService";
        this.kategoriBarangRepo = kategoriBarangRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Object> save(KategoriBarang kategoriBarang, HttpServletRequest request) {
        if(kategoriBarang==null)
        {
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    null,//object
                    "FV001001",//errorCode Fail Validation modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        try{
            kategoriBarangRepo.save(kategoriBarang);
        }catch (Exception e)
        {
            strExceptionArr[1] = "save(KategoriBarang kategoriBarang, HttpServletRequest request) --- LINE 59 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE001001",//errorCode Fail Error modul-code 001 sequence 001 range 001 - 010
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
    @Transactional(rollbackFor = {ArithmeticException.class})
    public ResponseEntity<Object> update(Long id,KategoriBarang kategoriBarang, HttpServletRequest request) {
        Optional<KategoriBarang> katBarang;
        KategoriBarang kBarang;
        Boolean isValid = true;
        try{
            katBarang =  kategoriBarangRepo.findById(id);

            if(katBarang.isEmpty())
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Valid",//message
                        HttpStatus.BAD_REQUEST,//httpstatus
                        null,//object
                        "FV001011",//errorCode Fail Validation modul-code 001 sequence 001 range 011 - 020
                        request
                );
            }

            kBarang = katBarang.get();
            kBarang.setNamaKategoriBarang(kategoriBarang.getNamaKategoriBarang());
        }
        catch (Exception e)
        {
            isValid = false;
            strExceptionArr[1] = " update(Long id,KategoriBarang kategoriBarang, HttpServletRequest request) --- LINE 101 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    null,//object
                    "FV001011",//errorCode Fail Validation modul-code 001 sequence 001 range 011 - 020
                    request
            );
        }
//        finally {
//            if(!isValid)
//            {
//                try
//                {
//                    throw new Exception("ERROR SCRIPT");
//                }catch (Exception e)
//                {
//                    throw new RuntimeException(e);
//                }
//            }
//        }

        return new ResponseHandler().generateResponse(
                "Data Berhasil Diubah",//message
                HttpStatus.CREATED,//httpstatus seharusnya no content 204 (permintaan berhasil tapi tidak ada content untuk dikirim dalam response)
                null,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Optional<KategoriBarang> katBarang =  kategoriBarangRepo.findById(id);

        if(katBarang.isEmpty())
        {
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    null,//object
                    "FV001021",//errorCode Fail Validation modul-code 001 sequence 001 range 021 - 030
                    request
            );
        }

        try{
            kategoriBarangRepo.deleteById(id);
        }catch (Exception e)
        {
            strExceptionArr[1] = "delete(Long id, HttpServletRequest request) --- LINE 164 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Dihapus",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE001021",//errorCode Fail Error modul-code 001 sequence 001 range 021 - 030
                    request
            );
        }
        return new ResponseHandler().generateResponse(
                "Data Berhasil Dihapus",//message
                HttpStatus.CREATED,//httpstatus seharusnya no content 204 (permintaan berhasil tapi tidak ada content untuk dikirim dalam response)
                null,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> saveBatch(List<KategoriBarang> lt, HttpServletRequest request) {

        /*
            Struk + Barang Detail
            // generate struk
            // save ke table header
            // save all ke table detail
         */
        if(lt.isEmpty())
        {
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    null,//object
                    "FV001031",//errorCode Fail Validation modul-code 001 sequence 001 range 031 - 040
                    request
            );
        }

        try{
            kategoriBarangRepo.saveAll(lt);
        }catch (Exception e)
        {
            strExceptionArr[1] = "saveBatch(List<KategoriBarang> lt, HttpServletRequest request) --- LINE 207 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE001031",//errorCode Fail Error modul-code 001 sequence 001 range 031 - 040
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
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {

        Optional<KategoriBarang> kategoriBarang ;
        KategoriBarangDTO kbdto;
        try{
            kategoriBarang  = kategoriBarangRepo.findById(id);

            if(kategoriBarang==null)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV001041",//errorCode Fail Validation modul-code 001 sequence 001 range 041 - 050
                        request
                );
            }
            kbdto = modelMapper.map(kategoriBarang, new TypeToken<KategoriBarangDTO>() {}.getType());

        }catch (Exception e)
        {
            strExceptionArr[1] = "findById(Long id, HttpServletRequest request) --- LINE 249 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE001041",//errorCode Fail Validation modul-code 001 sequence 001 range 041 - 050
                    request
            );
        }


        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                kbdto,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> findByPage(Integer page,
                                             Integer size,
                                             String columFirst,
                                             String valueFirst,
                                             HttpServletRequest request) {

        Page<KategoriBarang> pageKategoriBarang;
        List<KategoriBarangDTO> listKategoriBarangDTO;
        try
        {
            Pageable pageable = PageRequest.of(page,size, Sort.by("idKategoriBarang"));
            if(columFirst.equals("nama"))
            {
                pageKategoriBarang = kategoriBarangRepo.findByNamaKategoriBarangContains(pageable,valueFirst);
            } else {
                pageKategoriBarang = kategoriBarangRepo.findAll(pageable);
            }
            listKategoriBarangDTO = modelMapper.map(pageKategoriBarang.getContent(), new TypeToken<List<KategoriBarangDTO>>() {}.getType());
            int dataSize = pageKategoriBarang.getContent().size();

            if(dataSize==0)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV001051",//errorCode Fail Validation modul-code 001 sequence 001 range 051 - 060
                        request
                );
            }

        }catch (Exception e)
        {
            strExceptionArr[1] = "findByPage(Integer page, Integer size,String columFirst, String valueFirst, HttpServletRequest request) --- LINE 304 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE001051",//errorCode Fail Validation modul-code 001 sequence 001 range 051 - 060
                    request
            );
        }
        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                transformDataPaging.mapDataPaging(mapz,pageKategoriBarang,listKategoriBarangDTO),//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> findAllByPage(Integer page,
                                                Integer size,
                                                HttpServletRequest request) {
        Page<KategoriBarang> pageKategoriBarang;
        List<KategoriBarangDTO> listKategoriBarangDTO;

        try
        {
            Pageable pageable = PageRequest.of(page,size, Sort.by("idKategoriBarang"));
            pageKategoriBarang = kategoriBarangRepo.findAll(pageable);
            int dataSize = pageKategoriBarang.getContent().size();
            if(dataSize==0)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV001061",//errorCode Fail Validation modul-code 001 sequence 001 range 061 - 070
                        request
                );
            }
            listKategoriBarangDTO = modelMapper.map(pageKategoriBarang.getContent(), new TypeToken<List<KategoriBarangDTO>>() {}.getType());

        }catch (Exception e)
        {
            strExceptionArr[1] = "findAllByPage(Integer page, Integer size, HttpServletRequest request) --- LINE 346 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE001061",//errorCode Fail Validation modul-code 001 sequence 001 range 061 - 070
                    request
            );
        }
        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                transformDataPaging.mapDataPaging(mapz,pageKategoriBarang,listKategoriBarangDTO),//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        List<KategoriBarang> listKategoriBarang;
        try{
            listKategoriBarang = kategoriBarangRepo.findAll();
            if(listKategoriBarang.size()==0)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV001071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                        request
                );
            }
        }catch (Exception e)
        {
            strExceptionArr[1] = "findAll(HttpServletRequest request) --- LINE 382 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE001071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                listKategoriBarang,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> dataToExport(MultipartFile multipartFile, HttpServletRequest request) {
        try{

            if(!CsvReader.isCsv(multipartFile))
            {
                return new ResponseHandler().generateResponse(
                        "Tipe File tidak Valid",//message
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE,//httpstatus
                        null,//object
                        "FV001081",//errorCode Fail Validation modul-code 001 sequence 001 range 081 - 090
                        request
                );
            }

            List<KategoriBarang> listKategoriBarang = CsvReader.csvToKategoriBarangData(multipartFile.getInputStream());
            if(listKategoriBarang.size()==0)
            {
                return new ResponseHandler().generateResponse(
                        "Content tidak valid",//message
                        HttpStatus.BAD_REQUEST,//httpstatus
                        null,//object
                        "FV001082",//errorCode Fail Validation modul-code 001 sequence 001 range 081 - 090
                        request
                );
            }

            kategoriBarangRepo.saveAll(listKategoriBarang);

        }catch (Exception e)
        {
            strExceptionArr[1] = "dataToExport(MultipartFile multipartFile, HttpServletRequest request) --- LINE 433 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE001081",//errorCode Fail Validation modul-code 001 sequence 001 range 081 - 090
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Data Berhasil di Unggah",//message
                HttpStatus.CREATED,//httpstatus OK
                null,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }
}