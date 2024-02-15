package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Serializable>{
	
	public abstract Usuario findById(int id);
	public abstract Usuario findByEmail(String email);

}
