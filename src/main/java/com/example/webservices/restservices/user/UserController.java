package com.example.webservices.restservices.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

	@Autowired
	private UserDaoService service;

	@GetMapping("/users")
	public List<User> retrieveAll() {
		return service.findAll();
	}

	@GetMapping("/users/{id}")
	public User retrieveById(@PathVariable int id) {

		return service.findById(id);
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		service.save(user);

		return ResponseEntity.created(null).build();
	}

	/*@PostMapping("/single-file-upload")
	public ResponseEntity<Map<String, String>> handleFileUploadUsingCurl(
			@RequestParam("file") MultipartFile file) throws IOException {

		Map<String, String> map = new HashMap<>();

		// Populate the map with file details
		map.put("fileName", file.getOriginalFilename());
		*//*map.put("fileSize", file.getSize());*//*
		map.put("fileContentType", file.getContentType());

		// File upload is successful
		map.put("message", "File upload done");
		return ResponseEntity.ok(map);
	}*/
}
