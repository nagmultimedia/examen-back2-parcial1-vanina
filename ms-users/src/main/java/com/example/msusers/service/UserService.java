package com.example.msusers.service;

import com.example.msusers.client.ClientBill;
import com.example.msusers.models.BillDTO;
import com.example.msusers.models.User;
import com.example.msusers.repositories.SubscriptionRepository;
import com.example.msusers.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final List<User> userRepository;
    private UserRepository userR;
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ClientBill clientBill;

    public UserService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = List.of(new User("1", "Pedro", "PerezPereyra", "pobrePintorPortugues-pintaPreciososPaisajes.ParaPoderPartir@ParaParis.fr"));
    }

    public List<BillDTO> findById(String id) {
        return userR.findByUserId(id);
    }


    public List<User> getAllUser() {
        return userR.findAll();
    }
}
