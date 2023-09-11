package com.example.msusers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@JsonIgnoreProperties
@Entity
@Table(name = "billDTO")
public class BillDTO {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String idBill;
    private String customerBill;
    private String productBill;
    private Double totalPrice;

    public BillDTO(String idBill, String customerBill, String productBill, Double totalPrice) {
        this.idBill = idBill;
        this.customerBill = customerBill;
        this.productBill = productBill;
        this.totalPrice = totalPrice;
    }

    public BillDTO() {
    }

    public String getIdBill() {
        return idBill;
    }

    public String getCustomerBill() {
        return customerBill;
    }

    public void setCustomerBill(String customerBill) {
        this.customerBill = customerBill;
    }

    public String getProductBill() {
        return productBill;
    }

    public void setProductBill(String productBill) {
        this.productBill = productBill;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
