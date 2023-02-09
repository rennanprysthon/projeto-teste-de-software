package br.edu.ifpe.recife.testes.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import br.edu.ifpe.recife.testes.model.Client;
import br.edu.ifpe.recife.testes.model.Treino;
import br.edu.ifpe.recife.testes.repository.TreinoRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TreinoServiceTest {
	
		@MockBean
    	TreinoRepository repository;
	 	
		@InjectMocks
	    private TreinoService treinoService;
	 
	 
	 	@DisplayName("Test if findAll retrive all elements")
	    @Test
	    void shouldRetriveTreinoList() {
			List<Treino> treinos = LongStream.range(1, 5)
				.mapToObj(i ->Treino.builder().treinoA("Treino A")
            		.treinoB("Treino B")
            		.treinoC("Treino C")
            		.client(Client.builder().build()).build()).collect(Collectors.toList());
	 		
	 		Mockito.when(repository.findAll()).thenReturn(treinos);

	        List<Treino> fetched = treinoService.findAll();

	        Assertions.assertEquals(4, fetched.size());
	 }
	
}
