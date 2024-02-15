package com.example.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JWTAutenticator;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository usRep;

	/**
	 * Find a Usuario using her Id.
	 * 
	 * @param idRecogida
	 * @param request
	 * @return
	 */
	@GetMapping(path = "/findById", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO getUsuarioById(@RequestBody DTO requestId, HttpServletRequest request) {
		DTO obtainedUser = new DTO();
		Usuario u = usRep.findById(Integer.parseInt(requestId.get("id").toString()));

		if (u != null) {
			obtainedUser.put("id", u.getId());
			obtainedUser.put("user_name", u.getUserName());
			obtainedUser.put("name", u.getName().toString());
			obtainedUser.put("second_name", u.getSecondName().toString());
			obtainedUser.put("f_nac", u.getFNac().toString());
			obtainedUser.put("phone_number", u.getPhoneNumber());
			obtainedUser.put("email", u.getEmail().toString());
			obtainedUser.put("password", u.getPassword().toString());

		} else {
			obtainedUser.put("resultado", "nulo");
		}
		return obtainedUser;
	}

	/**
	 * 
	 * @param userData
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(path = "/autentifyUser", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO autentifyUser(@RequestBody DTO userData, HttpServletRequest request, HttpServletResponse response) {
		DTO result = new DTO();
		Usuario u = usRep.findByEmail(userData.get("email").toString());
		String tokken = "";

		if (u != null) {
			if (u.getPassword().toString().equals(userData.get("password"))) {
				result.put("result", "success");
				tokken = JWTAutenticator.createTokken(u);
//				Cookie cook = new Cookie("jwt", tokken);
//				cook.setMaxAge(7* 24 * 60 * 60);
//	            response.addCookie(cook);
				result.put("tokken", tokken);
			} else {
				result.put("result", "Contraseña incorrecta");
			}

		} else {
			result.put("result", "Email incorrecto");
		}
		return result;
	}

	/**
	 * Insert a new user
	 * 
	 * @param usuariaDTO
	 * @param request
	 * @return
	 */
	@PostMapping(path = "/insertUser", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO insertUsuario(@RequestBody DTO userDTO, HttpServletRequest request) {
		DTO result = new DTO();

		Usuario newUser = new Usuario();
	    Usuario existingUser = usRep.findByEmail(userDTO.get("email").toString());

		try {
			if(existingUser == null) {
				newUser.setUserName(userDTO.get("userName").toString());
				newUser.setName(userDTO.get("name").toString());
				newUser.setSecondName(userDTO.get("secondName").toString());
				newUser.setEmail(userDTO.get("email").toString());
				newUser.setPassword(userDTO.get("password").toString());
				newUser.setPhoneNumber(Integer.parseInt(userDTO.get("phone_number").toString()));
				newUser.setFNac(userDTO.get("f_nac").toString());

				usRep.save(newUser);

				result.put("result", "success");
			}
			else {
				result.put("result", "Usuario existente");
			}
		} catch (Exception e) {
			result.put("result", "No ha sido posible crear usuario: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

}