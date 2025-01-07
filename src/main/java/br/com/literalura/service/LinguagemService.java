package br.com.literalura.service;

import br.com.literalura.repository.LinguagemRepository;
import br.com.literalura.model.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinguagemService {
    @Autowired
    private LinguagemRepository linguagemRepository;

    public void listBooksByLanguage(String language){
        Optional<Language> isThereLanguage = linguagemRepository.findByLanguage(language);
        if(isThereLanguage.isPresent()){
            isThereLanguage.get().getBooks().stream()
                    .forEach(System.out::println);
        }
        else{
            System.out.println("Não foram encontrados livros com esse idioma");
        }
    }

    public void listAllLanguages(){
        List<Language> languages = linguagemRepository.findAll();
        languages.stream()
                .forEach(l -> System.out.println("Idioma: "+l.getLanguage()));
    }

}
