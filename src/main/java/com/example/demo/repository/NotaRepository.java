package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Nota;
import com.example.demo.model.Publication;

public interface NotaRepository extends JpaRepository< Nota, Serializable>{
 
	public abstract Nota findByPid(int pid);

	
}
