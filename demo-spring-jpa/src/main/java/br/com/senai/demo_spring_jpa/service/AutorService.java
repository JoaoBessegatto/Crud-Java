package br.com.senai.demo_spring_jpa.service;

import br.com.senai.demo_spring_jpa.dao.AutorDao;
import br.com.senai.demo_spring_jpa.entity.Autor;
import br.com.senai.demo_spring_jpa.exception.InvalidAutorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AutorService {

    private final AutorDao dao;

    public AutorService(AutorDao dao) {
        this.dao = dao;
    }

    public Autor save(Autor autor) {
        if (autor.getNome() == null || autor.getSobrenome() == null) {
            throw new InvalidAutorException("Nome e sobrenome são obrigatórios.");
        }
        return dao.save(autor);
    }

    public Autor update(Autor autor) {
        if (autor.getId() == null) {
            throw new InvalidAutorException("ID do autor é obrigatório para atualização.");
        }

        return dao.update(autor);
    }

    public boolean delete(Long id) {
        return dao.delete(id);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Transactional(readOnly = true)
    public Optional<Autor> findById(Long id) {
        return Optional.ofNullable(dao.findById(id));
    }

    @Transactional(readOnly = true)
    public List<Autor> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Autor> findAllByNomeOrSobrenome(String termo) {
        return dao.findAllByNomeOrSobrenome(termo);
    }

    @Transactional(readOnly = true)
    public Long autorCount() {
        return dao.autorCount();
    }

    @Transactional(readOnly = true)
    public boolean existsByNomeAndSobrenome(String nome, String sobrenome) {
        return dao.existsByNomeAndSobrenome(nome, sobrenome);
    }
}