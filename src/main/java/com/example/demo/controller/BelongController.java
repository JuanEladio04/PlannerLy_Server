package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/belong")
public class BelongController {
	@Autowired
	private BelongRepository belongRepository;

	@Autowired
	private UsuarioRepository uRep;

	@Autowired
	private WorkSpaceRepository wkRep;

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

	/**
	 * Returns all the users associated with a workspace using the jwt
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(path = "/getUsersOfWorkSpace/{id}")
	public List<DTO> getUsersOfWorkSpace(@PathVariable int id, HttpServletRequest request) {
		List<DTO> obtainedUsers = new ArrayList<DTO>();
		int workSpaceId = id;
		List<Usuario> users = belongRepository.findUsersByWorkSpaceId(workSpaceId);
		Usuario user = uRep.findById(JWTAutenticator.getUidFromJWRResquest(request));

		for (Usuario u : users) {
			if(user.getId() != u.getId()) {
				DTO obtainedUser = new DTO();
				obtainedUser.put("id", u.getId());
				obtainedUser.put("user_name", u.getUserName());
				obtainedUser.put("name", u.getName().toString());
				obtainedUser.put("second_name", u.getSecondName().toString());
				obtainedUser.put("f_nac", u.getFNac().toString());
				obtainedUser.put("phone_number", u.getPhoneNumber());
				obtainedUser.put("email", u.getEmail().toString());
				obtainedUser.put("password", u.getPassword().toString());
				obtainedUsers.add(obtainedUser);
			}
		}

		return obtainedUsers;
	}

	/**
	 * Insert a new user
	 * 
	 * @param usuariaDTO
	 * @param request
	 * @return
	 */
	@PostMapping(path = "/addUserToWorkSpace", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO insertBelong(@RequestBody DTO paramethersDTO, HttpServletRequest request) {
		DTO result = new DTO();
		Usuario u = uRep.findByEmail(paramethersDTO.get("email").toString());
		int wId = Integer.parseInt(paramethersDTO.get("workSpaceId").toString());
		WorkSpace wk = this.wkRep.findById(wId);
		Belong relation = new Belong();

		if (u != null) {
			Belong existingRelation = belongRepository.findByUsuarioAndWorkSpace(u, wk);
			if (existingRelation == null) {
				try {
					relation.setUsuario(u);
					relation.setWorkSpace(wk);
					belongRepository.save(relation);

					result.put("result", "success");
				} catch (Exception e) {
					result.put("result", "No ha sido posible añadir usuario al espacio de trabajo");
				}
			} else {
				result.put("result", "Este usuario ya ha sido añadido al espacio de trabajo");
			}
		} else {
			result.put("result", "Usuario no encontrado");
		}

		return result;
	}
	
	/**
	 * Eliminates the specified from the work space
	 * @param userId
	 * @param workSpaceId
	 * @return
	 */
    @DeleteMapping("/deleteRelation/{userId}/{workSpaceId}")
    public DTO deleteRelation(@PathVariable int userId, @PathVariable int workSpaceId) {
		DTO result = new DTO();
    	Usuario user = uRep.findById(userId);
    	WorkSpace workSpace = wkRep.findById(workSpaceId);
        Belong relation = belongRepository.findByUsuarioAndWorkSpace(user, workSpace);
        
        if (relation != null) {
            belongRepository.delete(relation);
            result.put("result", "success");
        } else {
        	result.put("result", "No ha sido posible eliminar usuario del expacio de trabajo");
        }
        
        return result;
    }

}
