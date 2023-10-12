package com.juaracoding.IRspringbootrestapi.controller;


import com.juaracoding.IRspringbootrestapi.dto.UsrDTO;
import com.juaracoding.IRspringbootrestapi.dto.UsrDTOToken;
import com.juaracoding.IRspringbootrestapi.handler.ResponseHandler;
import com.juaracoding.IRspringbootrestapi.model.Usr;
import com.juaracoding.IRspringbootrestapi.service.UsrService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/op")
public class OpenController {
    private UsrService usrService;
    private ModelMapper modelMapper;
    /*
        Wajib di class controller agar tidak terjadi cycle
     */
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public OpenController(UsrService usrService, ModelMapper modelMapper, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usrService = usrService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @PostMapping("/v1/gt")
    public ResponseEntity<Object> getToken(@RequestBody UsrDTO usrDTO, HttpServletRequest request) throws Exception{

        return usrService.authManager(usrDTO,request);
    }

    @PostMapping("/v1/regis")
    public ResponseEntity<Object> regis(@RequestBody UsrDTO usrDTO, HttpServletRequest request)
    {

        Usr usr = modelMapper.map(usrDTO, new TypeToken<Usr>() {}.getType());;
        return usrService.registrationUser(usr,request);
    }

    @PostMapping("/v1/regisv")
    public ResponseEntity<Object> regisVerification(@RequestBody UsrDTOToken usrDTOToken,
                                                    HttpServletRequest request)
    {
        String strToken = usrDTOToken.getToken();
        String strUserName = usrDTOToken.getUserName();
        return usrService.regisVerification(strUserName,strToken,request);
    }

    @PostMapping("/v1/ntoken")
    public ResponseEntity<Object> requestNewToken(@RequestParam(value = "uname") String strUserName,
                                                  HttpServletRequest request)
    {
        return usrService.requestNewToken(strUserName,request);
    }

    @PostMapping("/v1/getclaim")
    public ResponseEntity<Object> getClaims(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization").substring(7);

        return new ResponseHandler().generateResponse(
            "Akses Diterima",//message
            HttpStatus.OK,//httpstatus
            null,//object
            null,
            request
        );
    }

//    @GetMapping("/v1/checkToken")
//    public void testDoank(@RequestParam(value = "token") String strToken,@RequestParam(value = "tokenHash") String strHashToken)
//    {
//        System.out.println("Token is "+bCryptPasswordEncoder.matches(strToken,strHashToken));
//    }
}
