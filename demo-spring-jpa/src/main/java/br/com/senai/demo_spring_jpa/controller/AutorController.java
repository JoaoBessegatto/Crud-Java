package br.com.senai.demo_spring_jpa.controller;

import br.com.senai.demo_spring_jpa.dto.AutorResponseDTO;
import br.com.senai.demo_spring_jpa.entity.Autor;
import br.com.senai.demo_spring_jpa.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("autores")
public class AutorController {
    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Autor autor) {
        Autor savedAutor = service.save(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Autor criado com sucesso! ID: " + savedAutor.getId());
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Autor autor) {
        if (autor.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID do autor é obrigatório para atualização.");
        }

        if (autor.getNome() == null || autor.getSobrenome() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome e sobrenome são obrigatórios.");
        }

        service.update(autor);
        return ResponseEntity.ok("Autor atualizado com sucesso!");
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boolean deletado = service.delete(id);
        return deletado ? ResponseEntity.ok("Autor excluído com sucesso!")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado.");
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorResponseDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(autor -> ResponseEntity.ok(new AutorResponseDTO(autor)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> findAll() {
        List<Autor> autores = service.findAll();

        if (autores.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // Retorna lista vazia em vez de erro
        }

        List<AutorResponseDTO> dtos = autores.stream()
                .map(AutorResponseDTO::new)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("nomeOrSobrenome")
    public ResponseEntity<List<AutorResponseDTO>> findAllByNomeOrSobrenome(@RequestParam String termo) {
        List<Autor> autores = service.findAllByNomeOrSobrenome(termo);

        if (autores.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // Retorna lista vazia ao invés de erro
        }

        List<AutorResponseDTO> dtos = autores.stream()
                .map(AutorResponseDTO::new)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("autorCount")
    public ResponseEntity<Long> autorCount() {
        return ResponseEntity.ok(service.autorCount());
    }
}