package com.learning.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.Login;

@Repository
public interface LoginRepo extends JpaRepository<Login, String> {

	boolean existsByEmail(String email);
}
