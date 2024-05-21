package com.namasake.LoginAPI.repo;

import com.namasake.LoginAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);

}
