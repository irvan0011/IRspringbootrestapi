package com.juaracoding.IRspringbootrestapi.controller;

import com.juaracoding.IRspringbootrestapi.dto.CartDTO;
import com.juaracoding.IRspringbootrestapi.handler.ResponseHandler;
import com.juaracoding.IRspringbootrestapi.model.Cart;
import com.juaracoding.IRspringbootrestapi.service.CartService;
import com.juaracoding.IRspringbootrestapi.service.UsrService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final static String MODUL_NAME="Cart";

    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsrService usrService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/v1/sv")
    public ResponseEntity<Object> save(@RequestBody CartDTO cartDTO, HttpServletRequest request)
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
        Map<String,Object> map = usrService.checkAuthorization(token,MODUL_NAME,request);//menyamakan apakah modul sesuai dengan konfigurasi role nya...
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
        Cart cart = modelMapper.map(cartDTO, new TypeToken<Cart>() {}.getType());
        return cartService.save(cart,request);
    }
    @PutMapping("/v1/upd/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody CartDTO cartDTO, HttpServletRequest request)
            throws Exception
    {
        Cart cart = modelMapper.map(cartDTO, new TypeToken<Cart>() {}.getType());
        return cartService.update(id,cart,request);
    }
    @DeleteMapping("/v1/del/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id, HttpServletRequest request)
    {
        return cartService.delete(id,request);
    }
    @GetMapping("/v1/findall")
    public ResponseEntity<Object> findAll(HttpServletRequest request)
    {
        return cartService.findAll(request);
    }

}
