package com.usuario.service.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.model.Usuario;
import com.usuario.service.model.repository.UsuarioRepository;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;

@Service
public class UsuarioService {
	
	@Autowired
	private MotoFeignClient motoFeignClient;
	
	@Autowired
	private CarroFeignClient carroFeignClient;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UsuarioRepository usuarioRepositorio;
	
	
	//Restemplate
	public List<Carro> getCarros(int usuarioId){
		List<Carro> listarCarros = restTemplate.getForObject("http://localhost:8082/api/carros/usuario/" + usuarioId, List.class);
		return listarCarros;
		
	}
	//Restemplate
	public List<Moto> getMotos(int usuarioId){
		List<Moto> listarMotos = restTemplate.getForObject("http://localhost:8083/api/motos/usuario/"+ usuarioId, List.class);
		return listarMotos;
	}
	
	//Feign Client
	public Carro saveCarro(int usuarioId,Carro carro) {
		carro.setUsuarioId(usuarioId);
		Carro nuevoCarro = carroFeignClient.save(carro);
		
		return nuevoCarro;
	}
	//Feing client
	public Moto saveMoto(int usuarioId,Moto moto) {
		moto.setUsuarioId(usuarioId);
		Moto nuevaMoto= motoFeignClient.crearMoto(moto);
		return nuevaMoto;
	}
	
	public Map<String, Object> getUsuariosAndVehiculos(int usuarioId){
		Map<String, Object> resultado = new HashMap<>();
		Usuario usuario = usuarioRepositorio.findById(usuarioId).orElse(null);
		
		if(usuario == null) {
			resultado.put("Mensaje", "El usuario no existe");
			return resultado;
		}
		
		resultado.put("Usuario", usuario);
		
		List<Carro> carros = carroFeignClient.listarCarrosDelUsuario(usuarioId);
		if(carros.isEmpty()) {
			resultado.put("Carros", "El usuario no tiene carros");
		}else {
			resultado.put("Carros", carros);
		}
		
		List<Moto> motos = motoFeignClient.listarMotosDeUsuario(usuarioId);
		if(motos.isEmpty()) {
			resultado.put("Moto", "El usuario no tiene motos");
		}else {
			resultado.put("Motos", motos);
		}
		
		return resultado;
		
	}
	
	
	
	//Comun
	public List<Usuario> getAll(){
		return usuarioRepositorio.findAll();
	}
	
	public Usuario getUsuarioById(int id) {
		return usuarioRepositorio.findById(id).orElse(null);
	}
	
	public Usuario save(Usuario usuario) {
		Usuario nuevoUsuario = usuarioRepositorio.save(usuario);
		return nuevoUsuario;
	}

}
