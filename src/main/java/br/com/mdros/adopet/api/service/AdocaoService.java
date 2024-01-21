package br.com.mdros.adopet.api.service;

import br.com.mdros.adopet.api.dto.AdocaoDto.AprovacaoAdocaoDto;
import br.com.mdros.adopet.api.dto.AdocaoDto.ReprovacaoAdocaoDto;
import br.com.mdros.adopet.api.dto.AdocaoDto.SolicitacaoAdocaoDto;
import br.com.mdros.adopet.api.model.Adocao;
import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.model.Tutor;
import br.com.mdros.adopet.api.repository.AdocaoRepository;
import br.com.mdros.adopet.api.repository.PetRepository;
import br.com.mdros.adopet.api.repository.TutorRepository;
import br.com.mdros.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoService {

    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private List<ValidacaoSolicitacaoAdocao> validacoes;


    public void solicitar(SolicitacaoAdocaoDto dto){
        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        validacoes.forEach(v -> v.validar(dto));

        Adocao adocao = new Adocao(tutor, pet, dto.motivo());

        repository.save(adocao);

        emailService.enviarEmail(
                pet.getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " +pet.getAbrigo().getNome() +"!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação."
        );
    }
    public void aprovar(AprovacaoAdocaoDto dto){

        Adocao adocao = repository.getReferenceById(dto.idAdocao());
        adocao.marcarComoAprovada();

//        repository.save(adocao); Por carregar do banco de dados o objeto, qualquer mudança ja atualiza no banco automaticamente, não precisa do metodo save.

        emailService.enviarEmail(
                adocao.getTutor().getEmail(),
                "Adoção aprovada",
                "Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet."
        );
    }

    public void reprovar(ReprovacaoAdocaoDto dto){

        Adocao adocao = repository.getReferenceById(dto.idAdocao());
        adocao.marcarComoReprovada(dto.justificativa());
//        repository.save(adocao);

        emailService.enviarEmail(
                adocao.getTutor().getEmail(),
                "Adoção reprovada",
                "Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +" com a seguinte justificativa: " +adocao.getJustificativaStatus()
        );
    }
}
