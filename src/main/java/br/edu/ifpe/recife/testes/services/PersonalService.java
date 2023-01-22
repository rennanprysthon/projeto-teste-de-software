package br.edu.ifpe.recife.testes.services;

import br.edu.ifpe.recife.testes.model.Personal;
import br.edu.ifpe.recife.testes.repository.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalService {
    @Autowired
    private PersonalRepository personalRepository;

    public Personal register(Personal personal) {
        return this.personalRepository.save(personal);
    }
}
