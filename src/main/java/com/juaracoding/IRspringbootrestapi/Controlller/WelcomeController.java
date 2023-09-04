package com.juaracoding.IRspringbootrestapi.Controlller;

import com.juaracoding.IRspringbootrestapi.Model.Address;
import com.juaracoding.IRspringbootrestapi.Model.Peserta;
import com.juaracoding.IRspringbootrestapi.repo.PesertaRepo;
import com.juaracoding.IRspringbootrestapi.util.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/hello") // memudahkan konfigurasi untuk mengakses method didalam class
public class WelcomeController {

    @Value("${flag.logging}")
    private String StrY;
    @GetMapping("/permisi")
    public String firstPage(){
        return "welcome " + OtherConfiguration.getFlagLogging();
    }

    @PostMapping("/postAddress")
    public Map<String, Object> postAddress(@RequestBody Address address){
        Map<String,Object> obj = new HashMap<>();
        obj.put("Message","Berhasil Kirim");
        obj.put("Data", address);
        obj.put("Status","Single");
        return obj;
    }

    @PostMapping("/postParam")
    public String postParam(@RequestParam String firstName, String lastName, String action){
        StringBuilder str = new StringBuilder();
        str.append(firstName).append(" ").append(lastName).append(" ").append(action);

        return String.valueOf(str);
    }

    @PostMapping("/postPath/{firstName}/{lastName}/{action}")
    public  String postPath(@PathVariable(value = "firstName") String firstName,
                            @PathVariable(value = "lastName") String lastName,
                            @PathVariable(value = "action") String action)
        {
        StringBuilder str = new StringBuilder();
        str.append(firstName).append(" ").append(lastName).append(" ").append(action);

        return String.valueOf(str);
    }

    @PostMapping("/kirimFile")
    public String kirimFile(@RequestParam(value = "kiriman") MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uploadDir = "/user-files";
        try {
            FileUtility.saveFile(uploadDir,fileName,file);
        }catch (IOException e){
            throw new RuntimeException("Error"+e.getMessage());
        }
        return "Berhasil Menyimpan " + fileName;

    }

}
