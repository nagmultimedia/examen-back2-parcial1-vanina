package com.msbills.service;

import com.msbills.controller.BillController;
import com.msbills.models.Bill;
import com.msbills.repositories.BillRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private static BillRepository repository;

    public static List<Bill> getAllBill() {
        return repository.findAll();
    }

    public List<Bill> findAllByIdUser(String idUser) {
        return repository.findAllByIdUser(idUser);
    }

    public void registerBill(BillController.DocumentRequest request) {
        Bill bill = Bill.builder()
                .idBill(request.getIdBill())
                .customerBill(request.getCustomerBill())
                .productBill(request.getProductBill())
                .totalPrice(request.getTotalPrice())
                .build();

        repository.save(bill);
    }
}
