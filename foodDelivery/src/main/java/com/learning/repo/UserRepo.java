package com.learning.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	Boolean existsByEmail(String email);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	void deleteByUsername(String username);

}
