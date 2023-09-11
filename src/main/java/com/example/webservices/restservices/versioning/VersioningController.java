package com.example.webservices.restservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningController {

	@GetMapping("/v1/person")
	public PersonV1 getFirstVersion() {
		
		return new PersonV1("Sandeep Banoth");
	}

	@GetMapping("/v2/person")
	public PersonV2 getSecondVersion() {
		
		return new PersonV2(new Name("Sandeep","Banoth"));
	}
	
	@GetMapping(path = "/person", params = "version=1")
	public PersonV1 getFirstVersionRequest() {
		
		return new PersonV1("Sandeep Banoth");
	}
}
