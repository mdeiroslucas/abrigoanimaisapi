package br.com.mdros.adopet.api.exception;

public class TutorJaCadastradoNoSistemaException extends RuntimeException{
    public TutorJaCadastradoNoSistemaException(){
        super("Tutor já cadastrado no sistema");
    }
}
