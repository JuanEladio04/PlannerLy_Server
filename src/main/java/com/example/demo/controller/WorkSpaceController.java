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

import com.example.demo.model.Belong;
import com.example.demo.model.Usuario;
import com.example.demo.model.WorkSpace;
import com.example.demo.repository.BelongRepository;
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
	WorkSpaceRepository wkRep;
	@Autowired
	UsuarioRepository uRep;
	@Autowired
	BelongRepository bRep;

	/**
	 * Creates a new WorkSpace
	 * 
	 * @param usuariaDTO
	 * @param request
	 * @return
	 */
	@PostMapping(path = "/insertWorkSpace", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO insertWorkSpace(@RequestBody DTO wkDTO, HttpServletRequest request) {
		DTO result = new DTO();

		// Obtener el usuario actual
		Usuario user = uRep.findById(JWTAutenticator.getUidFromJWRResquest(request));

		if (user == null) {
			result.put("result", "Usuario no encontrado");
			return result;
		}

		try {
			// Validar los datos recibidos en el DTO
			String name = wkDTO.get("name").toString();
			String description = wkDTO.get("description").toString();
			if (name.isEmpty() || description.isEmpty()) {
				result.put("result", "El nombre y la descripción del espacio de trabajo son obligatorios");
				return result;
			}

			// Crear y guardar el nuevo espacio de trabajo
			WorkSpace newWK = new WorkSpace();
			newWK.setName(name);
			newWK.setDescription(description);
			WorkSpace savedWorkSpace = wkRep.save(newWK);

			// Crear y guardar la relación de pertenencia
			Belong newB = new Belong();
			newB.setUsuario(user);
			newB.setWorkSpace(savedWorkSpace);
			bRep.save(newB);

			result.put("result", "success");
		} catch (Exception e) {
			result.put("result", "No ha sido posible crear el espacio de trabajo: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

}
