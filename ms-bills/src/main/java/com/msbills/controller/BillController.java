package com.msbills.controller;

import com.msbills.models.Bill;
import com.msbills.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService service;
/*
    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAllforAll() {
        return ResponseEntity.ok().body(service.getAllBill());
    }
*/
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_user')")
    public ResponseEntity<List<Bill>> getAllForUser() {
        return ResponseEntity.ok().body(service.getAllBill());
    }

}
