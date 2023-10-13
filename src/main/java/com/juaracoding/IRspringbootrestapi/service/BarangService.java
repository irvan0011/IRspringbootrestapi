package com.juaracoding.IRspringbootrestapi.service;

import com.juaracoding.IRspringbootrestapi.configuration.OtherConfiguration;
import com.juaracoding.IRspringbootrestapi.core.IService;
import com.juaracoding.IRspringbootrestapi.dto.BarangDTO;
import com.juaracoding.IRspringbootrestapi.dto.KategoriBarangDTO;
import com.juaracoding.IRspringbootrestapi.handler.RequestCapture;
import com.juaracoding.IRspringbootrestapi.handler.ResponseHandler;
import com.juaracoding.IRspringbootrestapi.model.Barang;
import com.juaracoding.IRspringbootrestapi.model.Supplier;
import com.juaracoding.IRspringbootrestapi.repo.BarangRepo;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
    Modul Code 002
 */
@Service
//@Transactional
public class BarangService implements IService<Barang> {

    private BarangRepo barangRepo;
    private String [] strExceptionArr = new String[2];
    private TransformDataPaging transformDataPaging = new TransformDataPaging();
    private Map<String,Object> mapz = new HashMap<>();
    @Autowired
    private LogRequestRepo logRequestRepo;

    @Autowired
    private LogService logService;
    private ModelMapper modelMapper;

    public BarangService(BarangRepo barangRepo) {
        strExceptionArr[0] = "BarangService";
        this.barangRepo = barangRepo;
    }

    @Autowired
    public BarangService(BarangRepo barangRepo, ModelMapper modelMapper) {
        strExceptionArr[0] = "BarangService";
        this.barangRepo = barangRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Object> save(Barang barang, HttpServletRequest request) {
        if(barang==null)
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
            barangRepo.save(barang);

//            long idBarang=barang.getIdBarang();
//            List<Supplier> igsupp = barang.getListSupplier();
//            Long supp =igsupp.get(0).getIdSupplier();
//            barangRepo.saveBarang(idBarang,supp);

        }catch (Exception e)
        {
            strExceptionArr[1] = "save(Barang barang, HttpServletRequest request) --- LINE 59 \n"+ RequestCapture.allRequest(request);
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
    public ResponseEntity<Object> update(Long id, Barang barang, HttpServletRequest request) {
        Optional<Barang> opBarang;
        Barang barangTrans;
        Boolean isValid = true;

        try{
            opBarang =  barangRepo.findById(id);

            if(opBarang.isEmpty())
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Valid",//message
                        HttpStatus.BAD_REQUEST,//httpstatus
                        null,//object
                        "FV002011",//errorCode Fail Validation modul-code 001 sequence 001 range 011 - 020
                        request
                );
            }

            barangTrans = opBarang.get();
            //UNTUK METHOD PATCH
//            (barangTrans.getKategoriBarang()!=null || !barangTrans.getKategoriBarang().equals(""))?barangTrans.setKategoriBarang(barang.getKategoriBarang()):barangTrans.setKategoriBarang(barangTrans.setKategoriBarang());
            barangTrans.setNamaBarang(barang.getNamaBarang());
            barangTrans.setDeskripsi(barang.getDeskripsi());
            barangTrans.setKategoriBarang(barang.getKategoriBarang());

        }
        catch (Exception e)
        {
            isValid = false;
            strExceptionArr[1] = "update(Long id, Barang barang, HttpServletRequest request) --- LINE 119 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    null,//object
                    "FV002011",//errorCode Fail Validation modul-code 001 sequence 001 range 011 - 020
                    request
            );
        }

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
        Optional<Barang> barangTrans =  barangRepo.findById(id);

        if(barangTrans.isEmpty())
        {
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    null,//object
                    "FV002021",//errorCode Fail Validation modul-code 001 sequence 001 range 021 - 030
                    request
            );
        }

        try{
            barangRepo.deleteById(id);
        }catch (Exception e)
        {
            strExceptionArr[1] = "delete(Long id, HttpServletRequest request) --- LINE 164 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Dihapus",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002021",//errorCode Fail Error modul-code 001 sequence 001 range 021 - 030
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
    public ResponseEntity<Object> saveBatch(List<Barang> lt, HttpServletRequest request) {
        if(lt.isEmpty())
        {
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.BAD_REQUEST,//httpstatus
                    null,//object
                    "FV002031",//errorCode Fail Validation modul-code 001 sequence 001 range 031 - 040
                    request
            );
        }

        try{
            barangRepo.saveAll(lt);
        }catch (Exception e)
        {
            strExceptionArr[1] = "saveBatch(List<Barang> lt, HttpServletRequest request) --- LINE 207 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002031",//errorCode Fail Error modul-code 001 sequence 001 range 031 - 040
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
        Optional<Barang> barangTrans ;
        BarangDTO barangDTO;
        try{
            barangTrans  = barangRepo.findById(id);//select barang dari db
            if(barangTrans==null)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002041",//errorCode Fail Validation modul-code 001 sequence 001 range 041 - 050
                        request
                );
            }

            barangDTO = modelMapper.map(barangTrans, new TypeToken<KategoriBarangDTO>() {}.getType());

        }catch (Exception e)
        {
            strExceptionArr[1] = "findById(Long id, HttpServletRequest request) --- LINE 241 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002041",//errorCode Fail Validation modul-code 001 sequence 001 range 041 - 050
                    request
            );
        }


        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                barangDTO,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> findByPage(Integer page, Integer size, String columFirst, String valueFirst, HttpServletRequest request) {
        Page<Barang> pageKategoriBarang;
        List<BarangDTO> listKategoriBarangDTO;
        try
        {
            Pageable pageable = PageRequest.of(page,size, Sort.by("idBarang"));
            if(columFirst.equalsIgnoreCase("nama"))
            {
                pageKategoriBarang = barangRepo.findByNamaBarangContains(pageable,valueFirst);
            }else if(columFirst.equalsIgnoreCase("deskripsi"))
            {
                pageKategoriBarang = barangRepo.findByDeskripsiContains(pageable,valueFirst);
            }else {
                pageKategoriBarang = barangRepo.findAll(pageable);
            }
            listKategoriBarangDTO = modelMapper.map(pageKategoriBarang.getContent(), new TypeToken<List<BarangDTO>>() {}.getType());
            int dataSize = pageKategoriBarang.getContent().size();

            if(dataSize==0)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002051",//errorCode Fail Validation modul-code 001 sequence 001 range 051 - 060
                        request
                );
            }

        }catch (Exception e)
        {
            strExceptionArr[1] = "findByPage(Integer page, Integer size, String columFirst, String valueFirst, HttpServletRequest request) --- LINE 304 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002051",//errorCode Fail Validation modul-code 001 sequence 001 range 051 - 060
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
    public ResponseEntity<Object> findAllByPage(Integer page, Integer size, HttpServletRequest request) {
        Page<Barang> pageBarang;
        List<BarangDTO> listBarangDTO;
        try
        {
            Pageable pageable = PageRequest.of(page,size, Sort.by("idBarang"));
            pageBarang = barangRepo.findAll(pageable);
            int dataSize = pageBarang.getContent().size();
            if(dataSize==0)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002061",//errorCode Fail Validation modul-code 001 sequence 001 range 061 - 070
                        request
                );
            }
            listBarangDTO = modelMapper.map(pageBarang.getContent(), new TypeToken<List<BarangDTO>>() {}.getType());


        }catch (Exception e)
        {
            strExceptionArr[1] = "findAllByPage(Integer page, Integer size, HttpServletRequest request) --- LINE 346 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002061",//errorCode Fail Validation modul-code 001 sequence 001 range 061 - 070
                    request
            );
        }
        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                transformDataPaging.mapDataPaging(mapz,pageBarang,listBarangDTO),//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

    @Override
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        List<Barang> listBarang;
        try{
            listBarang = barangRepo.findAll();
            if(listBarang.size()==0)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Ditemukan",//message
                        HttpStatus.NOT_FOUND,//httpstatus
                        null,//object
                        "FV002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                        request
                );
            }
        }catch (Exception e)
        {
            strExceptionArr[1] = " findAll(HttpServletRequest request) --- LINE 382 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002071",//errorCode Fail Validation modul-code 001 sequence 001 range 071 - 080
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Data Ditemukan",//message
                HttpStatus.OK,//httpstatus OK
                listBarang,//object
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
                        "FV002081",//errorCode Fail Validation modul-code 001 sequence 001 range 081 - 090
                        request
                );
            }

            List<Barang> listBarang = CsvReader.csvToKategoriBarang(multipartFile.getInputStream());
            if(listBarang.size()==0)
            {
                return new ResponseHandler().generateResponse(
                        "Content tidak valid",//message
                        HttpStatus.BAD_REQUEST,//httpstatus
                        null,//object
                        "FV002082",//errorCode Fail Validation modul-code 001 sequence 001 range 081 - 090
                        request
                );
            }

            barangRepo.saveAll(listBarang);

        }catch (Exception e)
        {
            strExceptionArr[1] = "dataToExport(MultipartFile multipartFile, HttpServletRequest request) --- LINE 433 \n"+RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            return new ResponseHandler().generateResponse(
                    "Data tidak Valid",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE002081",//errorCode Fail Validation modul-code 001 sequence 001 range 081 - 090
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