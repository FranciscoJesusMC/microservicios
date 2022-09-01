package com.moto.service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moto.service.model.Moto;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoRepositorio extends JpaRepository<Moto, Integer> {
	
	List<Moto> findByUsuarioId(int usuarioId);

}
