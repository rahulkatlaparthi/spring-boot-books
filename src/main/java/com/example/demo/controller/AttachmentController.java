package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.exeception.BaseResponse;
import com.example.demo.model.Attachments;
import com.example.demo.repository.AttachmentRepository;

import jdk.internal.org.jline.utils.Log;

@RestController
public class AttachmentController {
	
	@Autowired
	private AttachmentRepository attachment;
	
	public String getExtension(String mimeType) {
		if (mimeType == null) {
			return null;
		}
		if (mimeType.contains("image/")) {
			return "." + mimeType.replace("image/", "");
		}
		return null;

	}
	
	@PostMapping("/attachment")
	public BaseResponse addAttachment(@RequestParam("file") MultipartFile file) throws IOException, NoSuchAlgorithmException, NoSuchProviderException {
		
		SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");
		String UPLOADED_FOLDER=new ClassPathResource("static/image/").getFile().getAbsolutePath();
		int r = secureRandomGenerator.nextInt();
		
		String str = r + getExtension(file.getContentType());
		System.out.println(UPLOADED_FOLDER);
		StringBuilder fileNames = new StringBuilder();
		Path fileNameAndPath = Paths.get(UPLOADED_FOLDER, str);
//		fileNames.append(str+" ");
		Files.write(fileNameAndPath, file.getBytes());
				
//	    file.transferTo(new File(UPLOADED_FOLDER,file.getOriginalFilename()));
        Attachments attach =new Attachments();
        Attachments attac =new Attachments();
        attach.setFilePath("/image/"+ str);
        attach.setMimeType(file.getContentType());
       attac= attachment.save(attach);

        return new BaseResponse(true, "Success", attac.getId());
        

	}
	
}
