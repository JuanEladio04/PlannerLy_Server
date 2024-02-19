package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Publication;
import com.example.demo.model.WorkSpace;


public interface PublicationRepository extends JpaRepository< Publication, Serializable>{

    List<Publication> findByWorkSpaceOrderByIdDesc(WorkSpace wk);

	}
	
