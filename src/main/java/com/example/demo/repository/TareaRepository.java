package com.example.demo.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Tarea;

public interface TareaRepository extends JpaRepository< Tarea, Serializable> {
	 
		public abstract Tarea findByPid(int pid);
}
