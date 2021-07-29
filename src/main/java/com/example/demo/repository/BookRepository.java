package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Books;

public interface BookRepository extends JpaRepository<Books,Integer>{
    
	@Query(value="SELECT * FROM books u WHERE u.userid =?1",nativeQuery=true)
	List<Books> findbyUserId(int userid);
    
	@Query(value="SELECT * FROM books u WHERE u.userid !=?1",nativeQuery=true)
	List<Books> findbyNotUserId(int userid);

}
