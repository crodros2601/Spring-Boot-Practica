package com.curso.Curso.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.curso.Curso.dao.UsuarioDao;
import com.curso.Curso.models.Usuario;
import com.curso.Curso.utils.JWTUtil;

@RestController
public class AuthController {
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private JWTUtil jwtUtil;

	@RequestMapping(value = "api/login", method = RequestMethod.POST)
	public String login(@RequestBody Usuario usuario) {
	
		Usuario usuarioLogueado = usuarioDao.obtenerUsuarioPorCredenciales(usuario);
		if(usuarioLogueado != null) {
			
			String token = jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuarioLogueado.getEmail());
			return token;
		}
		return "FAIL";
	}
	
}
