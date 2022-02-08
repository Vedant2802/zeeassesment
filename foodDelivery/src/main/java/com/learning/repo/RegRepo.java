package com.learning.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.Reg;

@Repository
public interface RegRepo extends JpaRepository<Reg, Integer> {

	boolean existsByEmail(String email);

}
