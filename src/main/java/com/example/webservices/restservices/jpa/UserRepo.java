package com.example.webservices.restservices.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webservices.restservices.user.User;

public interface UserRepo extends JpaRepository<User,Integer> {

	
	
}
