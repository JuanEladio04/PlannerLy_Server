package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the recordatorio database table.
 * 
 */
@Entity
@Table(name="recordatorio")
@NamedQuery(name="Recordatorio.findAll", query="SELECT r FROM Recordatorio r")
public class Recordatorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int pid;
	
	private String title;

	private byte completed;

	@Temporal(TemporalType.DATE)
	private String date;

	private String hour;

	//bi-directional one-to-one association to Publication
	@OneToOne
	@JoinColumn(name="pid")
	private Publication publication;

	public Recordatorio() {
	}

	public int getPid() {
		return this.pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte getCompleted() {
		return this.completed;
	}

	public void setCompleted(byte completed) {
		this.completed = completed;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String string) {
		this.date = string;
	}

	public String getHour() {
		return this.hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public Publication getPublication() {
		return this.publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

}