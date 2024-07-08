package com.curso.Curso.dao;

import java.util.List;

import com.curso.Curso.models.Usuario;

public interface UsuarioDao {
	
	List<Usuario> getUsuarios();

	void eliminar(Long id);

	void registrar(Usuario usuario);

	Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
}
