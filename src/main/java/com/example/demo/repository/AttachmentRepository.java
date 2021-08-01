package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Attachments;

public interface AttachmentRepository extends JpaRepository<Attachments,Integer>{

}
