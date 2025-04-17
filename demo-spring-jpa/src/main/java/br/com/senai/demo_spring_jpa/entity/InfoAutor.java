package br.com.senai.demo_spring_jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "info_autor")
public class InfoAutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_info")
    private Long id;

    @Column(name = "cargo", length = 45, nullable = false)
    private String cargo;

    @Column(name = "bio", length = 255, nullable = true)
    private String bio;

    @OneToOne(mappedBy = "infoAutor") // Define que Autor possui a relação
    @JsonBackReference // Indica que essa parte não deve ser serializada
    private Autor autor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // Adicionando método necessário
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Autor getAutor() {
        return autor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InfoAutor infoAutor = (InfoAutor) o;
        return Objects.equals(id, infoAutor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
