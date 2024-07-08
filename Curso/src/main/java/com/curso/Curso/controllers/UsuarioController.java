package com.curso.Curso.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.curso.Curso.dao.UsuarioDao;
import com.curso.Curso.models.Usuario;
import com.curso.Curso.utils.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@RequestMapping(value = "api/usuario/{id}", method = RequestMethod.GET)
	public Usuario getUsuario(@PathVariable Long id) {
		
		Usuario usuario = new Usuario();
		
		usuario.setId(id);
		usuario.setNombre("Carmelo");
		usuario.setApellido("Rodriguez");
		usuario.setEmail("carmelorodriguezrosalina@gmail.com");
		usuario.setTelefono("634846623");
		
		return usuario;
	}
	
	@RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
	public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token) {
	
		if(!validarToken(token)) {
			return null;
		}
		
		return usuarioDao.getUsuarios();
		
	}
	
	private boolean validarToken(String token) {
		String usuarioId = jwtUtil.getKey(token);
		return usuarioId != null;
	}
	
	@RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
	public void registrarUsuario(@RequestBody Usuario usuario) {
		
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
		String hash = argon2.hash(1,1024,1,usuario.getPassword());
		usuario.setPassword(hash);
	
		usuarioDao.registrar(usuario);
		
	}
	
	public Usuario editar() {
		
		Usuario usuario = new Usuario();
		
		return usuario;
	}
	
	@RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
	public void eliminar(@RequestHeader(value="Authorization") String token,
						 @PathVariable Long id) {
		
		if(!validarToken(token)) {
			return;
		}
		
		usuarioDao.eliminar(id);
		
	}
}
 