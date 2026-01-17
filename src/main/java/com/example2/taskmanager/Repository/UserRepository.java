package com.example2.taskmanager.Repository;

import org.springframework.stereotype.Repository;

import com.example2.taskmanager.Model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
}
