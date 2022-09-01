package com.usuario.service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.model.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		List<Usuario> usuarios = usuarioService.getAll();
		if(usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable(name = "usuarioId")int usuarioId){
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if(usuario ==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
		Usuario usuarioNuevo = usuarioService.save(usuario);
		return ResponseEntity.ok(usuarioNuevo);
	}
	
	
	@GetMapping("/carros/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarros(@PathVariable(name = "usuarioId")int usuarioId){
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}	
		List<Carro> listarCarros = usuarioService.getCarros(usuarioId);
		
		return ResponseEntity.ok(listarCarros);
	
	}
	
	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable(name = "usuarioId")int usuarioId){
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if(usuario == null) {
			return ResponseEntity.noContent().build();
		}
		List<Moto> listarMotos = usuarioService.getMotos(usuarioId);
		return ResponseEntity.ok(listarMotos);
		
	}
	
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable(name = "usuarioId")int usuarioId,@RequestBody Carro carro){
		Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
		return ResponseEntity.ok(nuevoCarro);
		
	}
	
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable(name = "usuarioId")int usuarioId,@RequestBody Moto moto){
		Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);
		return ResponseEntity.ok(nuevaMoto);
	}
	
	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable(name = "usuarioId")int usuarioId){
		Map<String, Object> resultado = usuarioService.getUsuariosAndVehiculos(usuarioId);
		return ResponseEntity.ok(resultado);
	}
}
