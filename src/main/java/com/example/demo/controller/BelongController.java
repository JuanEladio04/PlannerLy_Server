package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Usuario;
import com.example.demo.model.WorkSpace;
import com.example.demo.repository.BelongRepository;
import com.example.demo.security.JWTAutenticator;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/belong")
public class BelongController {
	@Autowired
	private BelongRepository belongRepository;

	/**
	 * Returns all the work spaces of an user using the jwt
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(path = "/getUsersWorkSpaces")
	public List<DTO> getUsersWorkSpaces(HttpServletRequest request) {
		List<DTO> obtainedWorkSpaces = new ArrayList<DTO>();
		int userId = JWTAutenticator.getUidFromJWRResquest(request);
		List<WorkSpace> workSpaces = belongRepository.findWorkSpacesByUserId(userId);

		for (WorkSpace workSpace : workSpaces) {
			DTO wsDTO = new DTO();
			wsDTO.put("id", workSpace.getId());
			wsDTO.put("name", workSpace.getName());
			wsDTO.put("description", workSpace.getDescription());
			obtainedWorkSpaces.add(wsDTO);
		}

		return obtainedWorkSpaces;
	}

}
