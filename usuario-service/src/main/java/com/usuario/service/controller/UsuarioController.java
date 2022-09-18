package com.usuario.service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

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
	
	@CircuitBreaker(name = "carroCB",fallbackMethod = "fallBackGetCarros")
	@GetMapping("/carros/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarros(@PathVariable(name = "usuarioId")int usuarioId){
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}	
		List<Carro> listarCarros = usuarioService.getCarros(usuarioId);
		
		return ResponseEntity.ok(listarCarros);
	
	}
	@CircuitBreaker(name = "motoCB",fallbackMethod = "fallBackGetMotos")
	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable(name = "usuarioId")int usuarioId){
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if(usuario == null) {
			return ResponseEntity.noContent().build();
		}
		List<Moto> listarMotos = usuarioService.getMotos(usuarioId);
		return ResponseEntity.ok(listarMotos);
		
	}
	
	@CircuitBreaker(name = "carroCB",fallbackMethod = "fallBackSaveCarros")
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable(name = "usuarioId")int usuarioId,@RequestBody Carro carro){
		Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
		return ResponseEntity.ok(nuevoCarro);
		
	}
	
	@CircuitBreaker(name = "motoCB",fallbackMethod = "fallBackSaveMotos")
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable(name = "usuarioId")int usuarioId,@RequestBody Moto moto){
		Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);
		return ResponseEntity.ok(nuevaMoto);
	}
	
	@CircuitBreaker(name = "todosCB",fallbackMethod = "fallBackGetTodos")
	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable(name = "usuarioId")int usuarioId){
		Map<String, Object> resultado = usuarioService.getUsuariosAndVehiculos(usuarioId);
		return ResponseEntity.ok(resultado);
	}
	
	//Metodos para nuestro fallbacks
	
	private ResponseEntity<List<Carro>> fallBackGetCarros (@PathVariable(name = "usuarioId")int usuarioId,RuntimeException exception){
		return new ResponseEntity("El usuario :"+ usuarioId +"tiene los carros en el taller",HttpStatus.OK);
	}
	
	private ResponseEntity<Carro> fallBackSaveCarros(@PathVariable(name = "usuarioId")int usuarioId,@RequestBody Carro carro,RuntimeException exception){
		return new ResponseEntity("El usuario :"+usuarioId+ "no tiene dinero para ese carro",HttpStatus.OK);
	}
	
	private ResponseEntity<List<Moto>> fallBackGetMotos(@PathVariable(name = "usuarioId")int usuarioId,RuntimeException exception){
		return new ResponseEntity("EL usuario :"+ usuarioId+ "tiene las motos en el taller",HttpStatus.OK);
	}
	
	private ResponseEntity<Moto> fallBackSaveMotos(@PathVariable(name = "usuarioId")int usuarioId,@RequestBody Moto moto,RuntimeException exception){
		return new ResponseEntity("El usuario :" + usuarioId + "no tiene dinero para esa moto",HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String,Object>> fallBackGetTodos(@PathVariable(name = "usuarioId")int usuarioId,RuntimeException exception){
		return new ResponseEntity("El usuario"+ usuarioId+ "tiene todos los vehiculos en el taller",HttpStatus.OK);
	}
	
	
	
	
	
}
