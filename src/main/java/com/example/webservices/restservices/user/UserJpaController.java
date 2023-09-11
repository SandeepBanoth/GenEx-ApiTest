package com.example.webservices.restservices.user;

import com.example.webservices.restservices.jpa.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserJpaController {

	@Autowired
	private UserDaoService service;
	
//	@Autowired
	private UserRepo repo;
	
	
	

	public UserJpaController(UserRepo repo) { 
		super(); this.repo = repo;
		}
	 

	@GetMapping("/jpa/users")
	public List<User> retrieveAll() {
		return repo.findAll();
	}

	@GetMapping("/jpa/users/{id}")
	public User retrieveById(@PathVariable int id) {

		return service.findById(id);
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		service.save(user);

		return ResponseEntity.created(null).build();
	}


}
