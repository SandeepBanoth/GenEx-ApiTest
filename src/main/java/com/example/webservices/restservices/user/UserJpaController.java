package com.example.webservices.restservices.user;

import com.example.webservices.restservices.jpa.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
		service.deleteById(id);

        System.out.println("User deleted");
	}

	//code for file upload
	@PostMapping("/jpa/users/single-file-upload")
            public ResponseEntity<Map<String, String>> handleFileUploadUsingCurl(
					@RequestParam("file") MultipartFile file) throws IOException {

        Map<String, String> map = new HashMap<>();

        // Populate the map with file details
        map.put("fileName", file.getOriginalFilename());
        /*map.put("fileSize", file.getSize());*/
        map.put("fileContentType", file.getContentType());

        // File upload is successful
        map.put("message", "File upload done");
        return ResponseEntity.ok(map);

    }

	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		service.save(user);

		return ResponseEntity.created(null).build();
	}


}
