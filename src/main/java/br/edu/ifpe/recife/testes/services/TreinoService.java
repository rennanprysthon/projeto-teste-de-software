package br.edu.ifpe.recife.testes.services;


import br.edu.ifpe.recife.testes.model.Treino;
import br.edu.ifpe.recife.testes.repository.TreinoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TreinoService {
	
	@Autowired
	private TreinoRepository treinoRepository;
	
	public List<Treino> findAll() {
		return treinoRepository.findAll();
	};
}
