package com.juaracoding.IRspringbootrestapi.controller;


import com.juaracoding.IRspringbootrestapi.dto.SupplierDTO;
import com.juaracoding.IRspringbootrestapi.model.Supplier;
import com.juaracoding.IRspringbootrestapi.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    private ModelMapper modelMapper;
    private final static String MODUL_NAME = "Supplier";
    private SupplierService supplierService;

    @Autowired
    public SupplierController(ModelMapper modelMapper, SupplierService supplierService) {
        this.modelMapper = modelMapper;
        this.supplierService = supplierService;
    }

    @PostMapping("/v1/sv")
    public ResponseEntity<Object> save(@Valid @RequestBody SupplierDTO supplierDTO, HttpServletRequest request)
    {
        Supplier supplier = modelMapper.map(supplierDTO, new TypeToken<Supplier>() {}.getType());
        return supplierService.save(supplier,request);
    }

    @PutMapping("/v1/upd/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody SupplierDTO supplierDTO, HttpServletRequest request)
            throws Exception
    {
        Supplier supplier = modelMapper.map(supplierDTO, new TypeToken<Supplier>() {}.getType());
        return supplierService.update(id,supplier,request);
    }

    @GetMapping("/v1/fabp/{page}/{size}")
    public ResponseEntity<Object> findAllByPage(
            @PathVariable(value = "page") Integer page,
            @PathVariable(value = "size") Integer size,
            HttpServletRequest request)
    {
        return supplierService.findAllByPage(page,size,request);
    }
}