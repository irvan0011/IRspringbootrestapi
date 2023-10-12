package com.juaracoding.IRspringbootrestapi.controller;


import com.juaracoding.IRspringbootrestapi.dto.UsrDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @PostMapping("/v1/regis")
    public String registration(@Valid @RequestBody UsrDTO usrDTO)
    {
        System.out.println("Masuk kesini !!");
        return "SUDAH OK";
    }

}
