package com.example.msusers.repositories;


import com.example.msusers.models.BillDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SubscriptionRepository {
    private FeignSubscriptionRepository feignSubscriptionRepository;
    public SubscriptionRepository(FeignSubscriptionRepository feignSubscriptionRepository) {
        this.feignSubscriptionRepository = feignSubscriptionRepository;
    }


    //este metodo me tiene en ascuas y es que lo cree haciendo tremendo lio desde el controller del user con DTO
    public BillDTO findByUserId(String userId){
        ResponseEntity<BillDTO> response = feignSubscriptionRepository.findByUserId(userId);
        return response.getBody();
    }


}
