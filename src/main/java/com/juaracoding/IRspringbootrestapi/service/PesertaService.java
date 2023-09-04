package com.juaracoding.IRspringbootrestapi.service;

import com.juaracoding.IRspringbootrestapi.Controlller.OtherConfiguration;
import com.juaracoding.IRspringbootrestapi.Model.Peserta;
import com.juaracoding.IRspringbootrestapi.repo.PesertaRepo;
import com.juaracoding.IRspringbootrestapi.util.LoggingFile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;

@Service
@Transactional
public class PesertaService {

    private String [] strExceptionArr = new String[2];
    private PesertaRepo pesertaRepo;

    public PesertaService(PesertaRepo pesertaRepo){
        strExceptionArr[0] = "PesertaService";
        this.pesertaRepo = pesertaRepo;
    }

    public void save(Peserta peserta){

        try{
            Integer inty = 1/0;
            pesertaRepo.save(peserta);

        }catch (Exception e){
            strExceptionArr[1]="";
            System.out.println("Flag Loging : "+OtherConfiguration.getFlagLogging());
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLogging());

        }
    }

}
