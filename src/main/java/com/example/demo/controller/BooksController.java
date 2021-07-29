package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.example.demo.exeception.*;
import com.example.demo.model.Books;
import com.example.demo.model.Users;

import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

@RestController
public class BooksController {
	
	@Value("${upoadDir}")
	private String uploadFolder;
	@Autowired
	private BookRepository book;
	@Autowired
	private UserRepository use;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public String getExtension(String mimeType) {
		if(mimeType==null) {
			return null;
		}
		if(mimeType.contains("image/")) {
			return "."+mimeType.replace("image/", "");
		}
		return null;
		
	}
	
	@PostMapping("/addbooks")
	public ResponseEntity<?>  addProduct(@RequestParam("title") String title,@RequestParam("description") String description,@RequestParam("author") String author,@RequestParam("quantity") int quantity,@RequestParam("price") double price,@RequestParam("userid") int userid,HttpServletRequest request
			,final @RequestParam("image") MultipartFile file,@RequestParam("mimeType") String mimeType) throws IOException {
		if()
		Optional<Users> us;
		Date date = new Date();
		long l=date.getTime();
		String str = Long.toString(l)+getExtension(mimeType);
		
		Map<String, Object> response = new HashMap<>();
		File file1 = new File("C:\\\\Users\\\\AnuSatya\\\\Documents\\\\workspace-spring-tool-suite-4-4.10.0.RELEASE\\\\springboot-book\\\\src\\\\main\\\\resources\\\\static\\\\image\\"+userid);
        if (!file1.exists()) {
            if (file1.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
		 String  uploadDirectory="C:\\Users\\AnuSatya\\Documents\\workspace-spring-tool-suite-4-4.10.0.RELEASE\\springboot-book\\src\\main\\resources\\static\\image\\"+userid;
		 StringBuilder fileNames=new StringBuilder();
		Path fileNameAndPath=Paths.get(uploadDirectory,str);
		fileNames.append(str+ " ");
		Files.write(fileNameAndPath, file.getBytes());
		Books books=new Books();
		books.setAuthor(author);
		books.setDescription(description);
		books.setPrice(price);
		books.setTitle(title);
		books.setQuantity(quantity);
//		books.setImage(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString());
		books.setImage("/image/"+file.getOriginalFilename());
		books.setUserid(userid);
		 us= use.findById(userid);
		 Users p=us.get();
        books.setUser(p);
        book.save(books);
        return new ResponseEntity<>("Product Saved With File - ", HttpStatus.OK);
//	
		
//		try {
//			log.info("upload Folder is: "+uploadFolder);
////			String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
//			String  uploadDirectory=new ClassPathResource("static/image/").getFile().getAbsolutePath();
////		String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
//		log.info("uploadDirectory:: " + uploadDirectory);
//		String fileName = file.getOriginalFilename();
//		String filePath = Paths.get(uploadDirectory, fileName).toString();
//		log.info("FileName: " + file.getOriginalFilename());
//		Date createDate = new Date();
//		
//		try {
//			File dir = new File(uploadDirectory);
//			if (!dir.exists()) {
//				log.info("Folder Created");
//				dir.mkdirs();
//			}
//			// Save the file locally
//			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
//			stream.write(file.getBytes());
//			stream.close();
//		} catch (Exception e) {
//			log.info("in catch");
//			e.printStackTrace();
//		}
//		byte[] imageData = file.getBytes();
//		Books books=new Books();
//		books.setAuthor(author);
//		books.setDescription(description);
//		books.setPrice(price);
//		books.setTitle(title);
//		books.setQuantity(quantity);
////		books.setImage(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString());
//		books.setImage("/image/"+file.getOriginalFilename());
//		books.setUserid(userid);
//		 us= use.findById(userid);
//		 Users p=us.get();
//        books.setUser(p);
//        book.save(books);
//        return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			log.info("Exception: " + e);
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//			
//		}
//	
        
		 
		
//	      us= use.findById(books.getUserid());
//	      Users p=us.get();
//	      books.setUser(p);
//		 Books bo=book.save(books);
//		 response.put("books", bo);
//		 return new BaseResponse(true,"Success",response);
	}
	
	@GetMapping("/allbooks")
	public List<Books> getBooks(){
		return book.findAll();
	}
	
	
	
	@PostMapping("/getbooks")
	public BaseResponse getBooks(@RequestBody Books books) {
		Map<String, Object> response = new HashMap<>();
		if(books.getIsExplore()) {
			
			List<Books> usd= book.findbyNotUserId(books.getUserid());
//			response.put("books", usd);
			return new BaseResponse(true,"Success",usd);
		}
		else {
		List<Books> usd= book.findbyUserId(books.getUserid());
//		response.put("books", usd);
		return new BaseResponse(true,"Success",usd);
		}
		
	}
	@DeleteMapping("/deleteproduct/{id}")
	public BaseResponse deleteProduct(@PathVariable int id) {
		Optional<Books> prod;
		prod=book.findById(id);
		if(prod.isPresent()) {
			book.deleteById(id);
			return new BaseResponse(true,"Success",prod);
		}
		return new BaseResponse(false,"Failed to delete",id);
		
		
	}
	

}
