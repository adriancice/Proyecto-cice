package com.adrian.app.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.adrian.app.entity.Usuario;

public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long> {

}
