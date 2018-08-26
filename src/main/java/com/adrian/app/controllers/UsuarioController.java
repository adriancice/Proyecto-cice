package com.adrian.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adrian.app.dao.IUsuarioDao;
import com.adrian.app.entity.Usuario;

@Controller
public class UsuarioController {

	@Autowired
	private IUsuarioDao usuarioDao;

	@RequestMapping(value = {"/", "/listar"}, method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("usuarios", usuarioDao.findAll());
		return "listar";
	}

	/**
	 * Mostramos el formulario 'signup' al usuario
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signup")
	public String crear(Map<String, Object> model) {

		Usuario usuario = new Usuario();
		model.put("usuario", usuario);
		model.put("titulo", "Registro de usuario");
		return "signup";
	}

	/**
	 * Metodo para procesar los datos enviados con el submit del formulario
	 * 
	 * @param usuario
	 * @return
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String guardar(Usuario usuario) {
		usuarioDao.save(usuario);
		return "redirect:listar";
	}
}
