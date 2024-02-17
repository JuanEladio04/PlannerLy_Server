package com.example.demo.model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "work_space")
public class WorkSpace implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    private String description;

    private String name;

    @OneToMany(mappedBy = "workSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Belong> belongs;

    @OneToMany(mappedBy = "workSpace")
    private List<Publication> publications;

    public WorkSpace() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Belong> getBelongs() {
        return belongs;
    }

    public void setBelongs(List<Belong> belongs) {
        this.belongs = belongs;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }
}
