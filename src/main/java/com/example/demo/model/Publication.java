package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "publication")
public class Publication implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(mappedBy = "publication", cascade = CascadeType.ALL)
    private Nota nota;
    
    @OneToOne(mappedBy = "publication", cascade = CascadeType.ALL)
    private Tarea tarea;
    
    @OneToOne(mappedBy = "publication", cascade = CascadeType.ALL)
    private Recordatorio recordatorio;

    @ManyToOne
    @JoinColumn(name = "wsid")
    private WorkSpace workSpace;

    public Publication() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public WorkSpace getWorkSpace() {
        return workSpace;
    }

    public void setWorkSpace(WorkSpace workSpace) {
        this.workSpace = workSpace;
    }

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public Recordatorio getRecordatorio() {
		return recordatorio;
	}

	public void setRecordatorio(Recordatorio recordatorio) {
		this.recordatorio = recordatorio;
	}
    
}
