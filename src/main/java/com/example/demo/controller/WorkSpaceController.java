package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Usuario;
import com.example.demo.model.WorkSpace;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.WorkSpaceRepository;
import com.example.demo.security.JWTAutenticator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/workspace")
public class WorkSpaceController {
	@Autowired
	WorkSpaceRepository wsRep;
	UsuarioRepository usRep;

//	public List<WorkSpace> getWorkspacesByUser(int userId) {
//		  String query = "SELECT w FROM WorkSpace w JOIN w.belongs b WHERE b.usuario = :usuario";
//		  TypedQuery<WorkSpace> typedQuery = EntityManager.createQuery(query, WorkSpace.class);
//		  
//		  typedQuery.setParameter("usuario", user);
//		  return typedQuery.getResultList();
//		}
//	
//	
//	@GetMapping(path = "/getWorkSpacesByUserId", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public DTO getWorkspacesByUser(@RequestBody DTO user, HttpServletRequest request, HttpServletResponse response) {
//		DTO result = new DTO();
//		Usuario u = usRep.findById(1);
//		String tokken = "";
//
//		if (u != null) {
//			if (u.getPassword().toString().equals(userData.get("password"))) {
//				result.put("result", "success");
//				tokken = JWTAutenticator.createTokken(u);
////				Cookie cook = new Cookie("jwt", tokken);
////				cook.setMaxAge(7* 24 * 60 * 60);
////	            response.addCookie(cook);
//				result.put("tokken", tokken);
//			} else {
//				result.put("result", "Contraseña incorrecta");
//			}
//
//		} else {
//			result.put("result", "Email incorrecto");
//		}
//		return result;
//	}

}
