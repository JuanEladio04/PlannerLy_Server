package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the belongs database table.
 * 
 */
@Embeddable
public class BelongPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int uid;

	@Column(insertable=false, updatable=false)
	private int wsid;

	public BelongPK() {
	}
	public int getUid() {
		return this.uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getWsid() {
		return this.wsid;
	}
	public void setWsid(int wsid) {
		this.wsid = wsid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BelongPK)) {
			return false;
		}
		BelongPK castOther = (BelongPK)other;
		return 
			(this.uid == castOther.uid)
			&& (this.wsid == castOther.wsid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.uid;
		hash = hash * prime + this.wsid;
		
		return hash;
	}
}