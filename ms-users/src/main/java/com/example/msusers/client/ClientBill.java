package com.example.msusers.client;

import com.example.msusers.models.BillDTO;
import com.example.msusers.models.User;
import lombok.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

/*
@FeignClient(name = "servicio-bills", url = "http://direccion-de-tu-servicio-bills")
public interface BillFeignClient {

    @GetMapping("/facturas")
    List<Factura> obtenerFacturasPorUsuario(@RequestParam("usuarioId") Long usuarioId);
}
 */
@FeignClient(name="api-bill", url = "http://localhost:8081")
public interface ClientBill {


    @PostMapping("/api/v1/bills/register")
    @PreAuthorize("hasRole('GROUP_PROVIDERS')")
    void registerBill(DocumentRequest request);

    @GetMapping("/find/{id}")
    List<BillDTO> findById(@PathVariable String id) ;


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
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
