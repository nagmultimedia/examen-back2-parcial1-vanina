package com.example.msusers.repositories;

import com.example.msusers.models.BillDTO;
import com.example.msusers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserRepository extends JpaRepository<User, String> {

    List<BillDTO> findByUserId(String userId);

}
