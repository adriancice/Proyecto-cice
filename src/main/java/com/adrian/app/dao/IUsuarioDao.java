package com.adrian.app.dao;

import java.util.List;

import com.adrian.app.entity.Usuario;

public interface IUsuarioDao {

	public List<Usuario> findAll();

	public void save(Usuario usuario);

}
