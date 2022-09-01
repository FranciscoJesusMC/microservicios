package com.moto.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moto.service.model.Moto;
import com.moto.service.service.MotoService;

@RestController
@RequestMapping("api/motos")
public class MotoController {

	@Autowired
	private MotoService motoService;
	
	
	@GetMapping
	public ResponseEntity<List<Moto>> listarMotos(){
		List<Moto> listarMotos = motoService.listAll();
		if(listarMotos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
			return ResponseEntity.ok(listarMotos);
	}
	
	@GetMapping("/{motoId}")
	public ResponseEntity<Moto> obtenerMotoPorId(@PathVariable(name = "motoId") int motoId){
		Moto buscarMoto = motoService.getMotoById(motoId);
		if(buscarMoto == null) {
			return ResponseEntity.notFound().build();
		}
			return ResponseEntity.ok(buscarMoto);
	}
	
	@PostMapping
	public ResponseEntity<Moto> crearMoto(@RequestBody Moto moto){
		return new ResponseEntity<>(motoService.save(moto),HttpStatus.OK);
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotosDeUsuario(@PathVariable(name = "usuarioId")int usuarioId){
		List<Moto> motoUsuarios = motoService.byUsuarioId(usuarioId);
		if(motoUsuarios == null) {
			return ResponseEntity.noContent().build();
		}
			return ResponseEntity.ok(motoUsuarios);
	}
}
