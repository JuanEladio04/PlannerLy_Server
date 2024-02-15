package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Belong;
import com.example.demo.model.WorkSpace;


@Repository
public interface BelongRepository extends JpaRepository<Belong, Serializable> {

    @Query("SELECT b.workSpace FROM Belong b WHERE b.usuario.id = :userId")
    List<WorkSpace> findWorkSpacesByUserId(@Param("userId") int userId);
}
