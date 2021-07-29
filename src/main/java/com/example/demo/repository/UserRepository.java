package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.example.demo.model.Users;



public interface UserRepository extends JpaRepository<Users,Integer>{
    
//	@Query(value="SELECT * FROM user  WHERE email = $email",nativeQuery=true)
	Users findByEmail( String email);

	
}