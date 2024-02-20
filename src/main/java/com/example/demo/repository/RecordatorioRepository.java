package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Recordatorio;


public interface RecordatorioRepository  extends JpaRepository< Recordatorio, Serializable> {
	 
		public abstract Recordatorio findByPid(int pid);
}
