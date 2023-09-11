package com.example.msusers.repositories;

import com.example.msusers.configuration.feign.OAuthFeignConfig;
import com.example.msusers.models.BillDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "ms-bills",url = "http://localhost:8081", configuration = OAuthFeignConfig.class)//FeignInterceptor.class
public interface FeignSubscriptionRepository {

    @RequestMapping(method = RequestMethod.GET,value = "/subscription/find")
    ResponseEntity<BillDTO> findByUserId(@RequestParam String userId);
}
