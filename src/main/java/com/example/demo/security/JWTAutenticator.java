package com.example.demo.security;

import java.security.Key;
import com.example.demo.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;



public class JWTAutenticator {

	private static Key key = null;

	/**
	 * Create a token for an auth user
	 * 
	 * @param user
	 * @return
	 */
	public static String createTokken(Usuario user) {
		String tokken = "";
		tokken = Jwts.builder().setSubject("" + user.getId()).signWith(SignatureAlgorithm.HS512, getGeneratedKey())
				.compact();
		return tokken;
	}

	/**
	 * 
	 * Generates a new key every time the server restart
	 * 
	 * @return
	 * 
	 */
	private static Key getGeneratedKey() {
		if (key == null) {
			key = MacProvider.generateKey();
		}
		return key;
	}
	

	/**
	 * Gets the uid from the JWT token in the request's Authorization header.
	 * @param request The HttpServletRequest object representing the HTTP request.
	 * @return The UID if successfully extracted from the JWT token, otherwise -1.
	 */
	public static int getUidFromJWRResquest(HttpServletRequest request) {
	    String authHeader = request.getHeader("Authorization");

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        try {
	            String jwt = authHeader.substring(7);
	            String userIdString = Jwts.parser().setSigningKey(getGeneratedKey()).parseClaimsJws(jwt).getBody().getSubject();
	            int userId = Integer.parseInt(userIdString);
	            return userId;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return -1; 
	        }
	    } else {
	        return -1;
	    }
	}


}
