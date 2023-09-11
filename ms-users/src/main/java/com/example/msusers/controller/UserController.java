package com.example.msusers.controller;

import com.example.msusers.models.BillDTO;
import com.example.msusers.models.User;
import com.example.msusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService service;

    public UserController (UserService userService){
        service = userService;
    }

/*
    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAllforAll() {
        return ResponseEntity.ok().body(service.getAllBill());
    }
*/
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_user')")
    public ResponseEntity<List<User>> getAllForUser() {
        return ResponseEntity.ok().body(service.getAllUser());
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<List<BillDTO>> findById(@PathVariable String id) {
        List<BillDTO> bills = service.findById(id);
        List<BillDTO> billDTOs = bills.stream()
                .map(bill -> mapToDTO(bill))
                .collect(Collectors.toList());
        return ResponseEntity.ok(billDTOs);
    }

    public BillDTO mapToDTO(BillDTO bill) {
        BillDTO billDTO = new BillDTO();
        billDTO.setCustomerBill(bill.getCustomerBill());
        billDTO.setProductBill(bill.getProductBill());
        billDTO.setTotalPrice(bill.getTotalPrice());
        return billDTO;
    }

}
