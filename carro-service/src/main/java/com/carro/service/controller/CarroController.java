package com.carro.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carro.service.model.Carro;
import com.carro.service.servicio.CarroServicio;

@RestController
@RequestMapping("/api/carros")
public class CarroController {
	
	@Autowired
	private CarroServicio servicio;
	
	@GetMapping
	public ResponseEntity<List<Carro>> listarCarros(){
		List<Carro> carros = servicio.findAll();
		if(carros == null) {
			return ResponseEntity.noContent().build();
		}
			return ResponseEntity.ok(carros);
	}
	
	
	@GetMapping("/{carroId}")
	public ResponseEntity<Carro> obtenerCarro(@PathVariable(name = "carroId")int carroId){
		Carro carro = servicio.getCarroById(carroId);
		if(carro == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(carro);
	}
	
	@PostMapping
	public ResponseEntity<Carro> crearCarro(@RequestBody Carro carro){
		Carro nuevoCarro = servicio.save(carro);
		return ResponseEntity.ok(nuevoCarro);
		
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarrosDeusuario(@PathVariable(name = "usuarioId")int usuarioId){
		List<Carro> listarCarros = servicio.byUsuarioId(usuarioId);
		if(listarCarros == null) {
			return ResponseEntity.noContent().build();
		}
			return ResponseEntity.ok(listarCarros);
		
	}

}
