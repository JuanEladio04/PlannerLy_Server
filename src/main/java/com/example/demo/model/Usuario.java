package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String email;

	@Column(name="f_nac")
	private String fNac;

	private String name;

	private String password;

	@Column(name="phone_number")
	private int phoneNumber;

	@Column(name="second_name")
	private String secondName;

	@Column(name="user_name")
	private String userName;

	//bi-directional many-to-one association to Belong
	@OneToMany(mappedBy="usuario")
	private List<Belong> belongs;

	public Usuario() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFNac() {
		return this.fNac;
	}

	public void setFNac(String fNac) {
		this.fNac = fNac;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSecondName() {
		return this.secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Belong> getBelongs() {
		return this.belongs;
	}

	public void setBelongs1(List<Belong> belongs1) {
		this.belongs = belongs1;
	}

	public Belong addBelongs1(Belong belongs1) {
		getBelongs().add(belongs1);
		belongs1.setUsuario(this);

		return belongs1;
	}

	public Belong removeBelongs1(Belong belongs1) {
		getBelongs().remove(belongs1);
		belongs1.setUsuario(null);

		return belongs1;
	}

}