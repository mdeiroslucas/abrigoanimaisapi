package br.com.mdros.adopet.api.model;

import br.com.mdros.adopet.api.dto.TutorDto.AtualizarTutorDto;
import br.com.mdros.adopet.api.dto.TutorDto.CadastrarTutorDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tutores")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private String email;

    @OneToMany(mappedBy = "tutor")
    private List<Adocao> adocoes;

    public Tutor(){}

    public Tutor(CadastrarTutorDto tutorDto){
        this.nome = tutorDto.nome();
        this.email = tutorDto.email();
        this.telefone = tutorDto.telefone();
    }

    public void atualizarInformacoes(AtualizarTutorDto tutorDto){
        if (tutorDto.nome() != null) {
            this.nome = tutorDto.nome();
        }
        if (tutorDto.telefone() != null) {
            this.telefone = tutorDto.telefone();
        }
        if (tutorDto.email() != null) {
            this.email = tutorDto.email();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return Objects.equals(id, tutor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Adocao> getAdocoes() {
        return adocoes;
    }

    public void setAdocoes(List<Adocao> adocoes) {
        this.adocoes = adocoes;
    }
}
