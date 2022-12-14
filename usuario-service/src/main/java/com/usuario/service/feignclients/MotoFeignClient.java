package com.usuario.service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.usuario.service.modelos.Moto;

@FeignClient(name = "moto-service",url = "http://localhost:8083")
@RequestMapping("/api/motos")
public interface MotoFeignClient {
	
	
	@PostMapping
	public Moto crearMoto(@RequestBody Moto moto);
	
	@GetMapping("/usuario/{usuarioId}")
	public List<Moto> listarMotosDeUsuario(@PathVariable(name = "usuarioId")int usuarioId);

}
