
package com.securerealty.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securerealty.backend.Service.ChatService;
import com.securerealty.backend.Service.EncryptionService;
import com.securerealty.backend.dto.EncryptedData;

@RestController
public class TestController {
	
	private final EncryptionService encryptionService;

	public TestController(EncryptionService encryptionService) {
	    this.encryptionService = encryptionService;
	}
	@GetMapping("/test-encryption")
	public String testEncryption() throws Exception {
		
		
	    EncryptedData data =
	            encryptionService.encrypt("Hello SecureRealty");

	    System.out.println("Ciphertext:");
	    System.out.println(data.getCipherText());

	    System.out.println("IV:");
	    System.out.println(data.getIv());
	    String plain =
	            encryptionService.decrypt(
	                    data.getCipherText(),
	                    data.getIv());

	    System.out.println("Plain:");
	    System.out.println(plain);

	    return "Check console";
	}

}