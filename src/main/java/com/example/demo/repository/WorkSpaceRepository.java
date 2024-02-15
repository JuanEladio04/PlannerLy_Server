package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Usuario;
import com.example.demo.model.WorkSpace;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace, Serializable>{
	
	
}
