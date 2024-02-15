package com.example.demo.security;

import java.io.IOException;

import jakarta.servlet.Filter;

import jakarta.servlet.FilterChain;

import jakarta.servlet.FilterConfig;

import jakarta.servlet.ServletException;

import jakarta.servlet.ServletRequest;

import jakarta.servlet.ServletResponse;

import jakarta.servlet.annotation.WebFilter;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")

public class MyWebFilter implements Filter {

	@Override

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String uriDePeticionWeb = request.getRequestURI();
		String metodoRequerido = request.getMethod();
		int idUsuarioAutenticadoMedianteJWT = JWTAutenticator.getUidFromJWRResquest(request);

		if (metodoRequerido.equalsIgnoreCase("OPTIONS") || uriDePeticionWeb.startsWith("/")
				|| uriDePeticionWeb.equals("/usuaria/autentica") || idUsuarioAutenticadoMedianteJWT != -1) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			response.sendError(403, "No autorizado");
		}
	}

	@Override

	public void destroy() {

	}

}