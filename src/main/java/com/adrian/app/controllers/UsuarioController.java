package com.adrian.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adrian.app.entity.Usuario;
import com.adrian.app.service.IUsuarioService;
import com.adrian.app.util.paginator.PageRender;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@RequestMapping(value = { "/listar" }, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Usuario> usuarios = usuarioService.findAll(pageRequest);

		PageRender<Usuario> pageRender = new PageRender<>("/listar", usuarios);
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);
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
	 * Metodo para editar los usuarios
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signup/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Usuario usuario = null;
		if (id > 0) {
			usuario = usuarioService.findOne(id);
			if (usuario == null) {
				flash.addFlashAttribute("error", "El id del cliente no existe en la bd!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El id del cliente no puede ser 0!");
			return "redirect:/listar";
		}
		model.put("usuario", usuario);
		model.put("titulo", "Editar Cliente");
		return "signup";
	}

	/**
	 * Metodo para procesar los datos enviados con el submit del formulario
	 * 
	 * @param usuario
	 * @return
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String guardar(Usuario usuario, RedirectAttributes flash, SessionStatus status) {
		String mensajeFlash = (usuario.getId() != null) ? "Cliente editado con exito!" : "Cliente creado con exito!";

		usuarioService.save(usuario);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			usuarioService.delete(id);
		}
		flash.addFlashAttribute("success", "Usuario eliminado con exito");
		return "redirect:/listar";
	}

	@RequestMapping(value = "/login")
	public String login(Model model) {

		return "/login";
	}

}
