package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Usuario;
import com.example.demo.repository.PublicationRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JWTAutenticator;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/publication")
public class PublicationController {
	
	@Autowired
	private PublicationRepository puRep;
	@Autowired
	private UsuarioRepository usuRep;
	
	/**
	 * Deletes a work space using the specified id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deleteById/{id}")
	public DTO deletePublicationById(@PathVariable int id, HttpServletRequest request) {
		DTO result = new DTO();

		try {
			Usuario user = usuRep.findById(JWTAutenticator.getUidFromJWRResquest(request));
			if (user.getId() > -1) {
				puRep.deleteById(id);
				result.put("result", "success");
			} else {
				result.put("result", "Usuario no válido");
			}
		} catch (Exception e) {
			result.put("result", "No ha sido posible borrar publicación");

		}
		

		return result;
	}

}
