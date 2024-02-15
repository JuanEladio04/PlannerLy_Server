package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the belongs database table.
 * 
 */
@Entity
@Table(name="belongs")
@NamedQuery(name="Belong.findAll", query="SELECT b FROM Belong b")
public class Belong implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BelongPK id;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="uid")
	private Usuario usuario;

	//bi-directional many-to-one association to WorkSpace
	@ManyToOne
	@JoinColumn(name="wsid")
	private WorkSpace workSpace;

	public Belong() {
	}

	public BelongPK getId() {
		return this.id;
	}

	public void setId(BelongPK id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public WorkSpace getWorkSpace() {
		return this.workSpace;
	}

	public void setWorkSpace1(WorkSpace workSpace) {
		this.workSpace = workSpace;
	}

}