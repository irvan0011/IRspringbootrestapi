package com.juaracoding.IRspringbootrestapi.Controlller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loadtest")
public class LoadController {
    private int countJmeter = 0;

    @GetMapping("/hit")
    public void justHit(){
        System.out.println("Counter Jmeter ini yang ke "+(countJmeter++));
    }
}
