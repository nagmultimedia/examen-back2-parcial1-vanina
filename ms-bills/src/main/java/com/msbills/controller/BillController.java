package com.msbills.controller;

import com.msbills.models.Bill;
import com.msbills.service.BillService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public abstract class BillController {

    private static BillService service;

    public BillController(BillService service) {
        this.service = service;
    }

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

    @GetMapping("/{idUser}")
    public ResponseEntity<List<Bill>> findAllByIdUser(String idUser) {
        return ResponseEntity.ok().body(service.findAllByIdUser(idUser));
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('SCOPE_userScope')") //PROVIDERS?
    public void registerBill(DocumentRequest request){
        service.registerBill(request);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public
    class DocumentRequest {
        @Size(min = 1, max = 10)
        private String idBill;
        @Size(min = 1, max = 20)
        private String customerBill;
        @Size(min = 1, max = 20)
        private String productBill;
        @Size(min = 1, max = 20)
        private Double totalPrice;
    }


}
