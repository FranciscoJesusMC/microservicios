package com.moto.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moto.service.model.Moto;
import com.moto.service.repository.MotoRepositorio;

@Service
public class MotoService {
	
	@Autowired
	private MotoRepositorio motoRepositorio;
	
	public List<Moto> listAll(){
		return motoRepositorio.findAll();
	}
	
	public Moto getMotoById(int id) {
		return motoRepositorio.findById(id).orElse(null);
	}
	
	public Moto save(Moto moto) {
		Moto nuevaMoto = motoRepositorio.save(moto);
		return nuevaMoto;
	
	}
	
	public List<Moto> byUsuarioId(int usuarioId){
		return motoRepositorio.findByUsuarioId(usuarioId);
	}

}
