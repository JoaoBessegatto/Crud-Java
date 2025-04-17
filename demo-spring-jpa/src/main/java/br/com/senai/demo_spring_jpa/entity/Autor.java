package br.com.senai.demo_spring_jpa.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="autor")
public class Autor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor", nullable = false)
    private Long id;

    @Column(name = "nome", length = 45, nullable = false)
    private String nome;

    @Column(name = "sobrenome", length = 45, nullable = false)
    private String sobrenome;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_info")
    @JsonManagedReference // Define como parte gerenciadora do relacionamento
    private InfoAutor infoAutor;

    public InfoAutor getInfoAutor() {
        return infoAutor;
    }

    public void setInfoAutor(InfoAutor infoAutor) {
        if (infoAutor == null) {
            throw new IllegalArgumentException("InfoAutor não pode ser nulo!");
        }
        this.infoAutor = infoAutor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return Objects.equals(id, autor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", infoAutor=" + (infoAutor != null ? infoAutor.getCargo() + " - " + infoAutor.getBio() : "Sem info") +
                '}';
    }
}