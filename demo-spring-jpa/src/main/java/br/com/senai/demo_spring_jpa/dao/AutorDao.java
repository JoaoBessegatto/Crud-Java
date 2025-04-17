package br.com.senai.demo_spring_jpa.dao;

import br.com.senai.demo_spring_jpa.entity.Autor;
import br.com.senai.demo_spring_jpa.entity.InfoAutor;
import br.com.senai.demo_spring_jpa.exception.AutorNotFoundException;
import br.com.senai.demo_spring_jpa.exception.InvalidAutorException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AutorDao {
    @PersistenceContext
    private EntityManager manager;

    public boolean existsByNomeAndSobrenome(String nome, String sobrenome) {
        return manager.createQuery(
                        "SELECT COUNT(a) > 0 FROM Autor a WHERE a.nome = :nome AND a.sobrenome = :sobrenome",
                        Boolean.class)
                .setParameter("nome", nome)
                .setParameter("sobrenome", sobrenome)
                .getSingleResult();
    }

    @Transactional
    public Autor save(Autor autor) {
        if (existsByNomeAndSobrenome(autor.getNome(), autor.getSobrenome())) {
            throw new InvalidAutorException("Já existe um autor com este nome e sobrenome.");
        }
        manager.persist(autor);
        return autor;
    }

    @Transactional
    public Autor update(Autor autor) {
        Autor autorExistente = manager.find(Autor.class, autor.getId());

        if (autorExistente == null) {
            throw new AutorNotFoundException("Autor não encontrado.");
        }

        // Atualiza apenas os atributos não nulos de InfoAutor
        if (autor.getInfoAutor() != null) {
            InfoAutor infoExistente = autorExistente.getInfoAutor();
            InfoAutor infoAtualizada = autor.getInfoAutor();

            if (infoAtualizada.getCargo() != null) infoExistente.setCargo(infoAtualizada.getCargo());
            if (infoAtualizada.getBio() != null) infoExistente.setBio(infoAtualizada.getBio());

            autorExistente.setInfoAutor(infoExistente);
        }

        return manager.merge(autorExistente);
    }


    @Transactional
    public boolean delete(Long id){
        Autor autor = this.manager.find(Autor.class, id);
        if (autor != null) {
            this.manager.remove(autor);
            return true;
        } else {
            throw new AutorNotFoundException("Autor com id " + id + " não encontrado.");
        }
    }

    @Transactional(readOnly = true)
    public Autor findById(Long id){
        Autor autor = this.manager.find(Autor.class, id);
        if (autor == null) {
            throw new AutorNotFoundException("Autor com id " + id + " não encontrado.");
        }
        return autor;
    }

    @Transactional(readOnly = true)
    public List<Autor> findAll() {
        List<Autor> autores = this.manager.createQuery("select a from Autor a", Autor.class)
                .getResultList();

        if (autores.isEmpty()) {
            throw new AutorNotFoundException("Nenhum autor encontrado.");
        }
        return autores;
    }

    @Transactional(readOnly = true)
    public List<Autor> findAllByNomeOrSobrenome(String termo) {
        String query = "SELECT a FROM Autor a WHERE LOWER(a.nome) LIKE LOWER(:termo) OR LOWER(a.sobrenome) LIKE LOWER(:termo)";
        List<Autor> autores = manager.createQuery(query, Autor.class)
                .setParameter("termo", "%" + termo + "%")
                .getResultList();

        if (autores.isEmpty()) {
            throw new AutorNotFoundException("Nenhum autor encontrado com o termo: " + termo);
        }

        return autores;
    }

    @Transactional(readOnly = true)
    public Long autorCount(){
        String query = "select count(a) from Autor a";
        Long count = this.manager.createQuery(query, Long.class).getSingleResult();

        return (count != null) ? count : 0L; // Garante que nunca retorna null
    }
}