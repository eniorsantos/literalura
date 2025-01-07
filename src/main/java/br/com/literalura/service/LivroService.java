package br.com.literalura.service;

import br.com.literalura.model.*;
import br.com.literalura.repository.LivroRepository;
import br.com.literalura.repository.LinguagemRepository;
import br.com.literalura.repository.AutorRepository;
//import com.literalura.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LinguagemRepository linguagemRepository;

    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "http://gutendex.com/books/";

    public void addBook(String nomeLivro) {
        var json = consumo.obterDados(ENDERECO + "?search=" + nomeLivro.replace(" ", "%20"));
        ApiData apiData = conversor.obterDados(json, ApiData.class);

        System.out.println(apiData);

        if (apiData.count() > 0) {
            saveBook(apiData.results());

            while (apiData.next() != null) {
                json = consumo.obterDados(ENDERECO + "?search=" + nomeLivro.replace(" ", "%20"));
                apiData = conversor.obterDados(json, ApiData.class);
                saveBook(apiData.results());
            }
        }
    }

    public void saveBook(List<BookData> books) {
        books.stream().forEach(book -> {
                    Book newBook = new Book(book);
                    Optional<Book> isThisBookRegistered = livroRepository.findByPublicId(newBook.getPublicId());
                    if (!isThisBookRegistered.isPresent()) {
                        try {
                            List<Person> authors = newBook.getAuthors().stream()
                                    .map(author -> {
                                        Optional<Person> isAuthorThere = autorRepository.findByName(author.getName());
                                        if (isAuthorThere.isPresent()) {
                                            return isAuthorThere.get();
                                        } else {
                                            autorRepository.save(author);
                                            return author;
                                        }
                                    })
                                    .collect(Collectors.toList());

                            List<Language> languages = newBook.getLanguages().stream()
                                    .map(language -> {
                                        Optional<Language> isLanguageThere = linguagemRepository.findByLanguage(language.getLanguage());

                                        if (isLanguageThere.isPresent()) {
                                            return isLanguageThere.get();
                                        } else {
                                            linguagemRepository.save(language);
                                            return language;
                                        }
                                    }).collect(Collectors.toList());

                            newBook.setAuthors(authors);
                            newBook.setLanguages(languages);


                            livroRepository.save(newBook);
                        } catch (DataIntegrityViolationException e) {
                            System.out.println("Livro já existe na base de dados");
                        }
                    }

                    System.out.println(newBook);
                }
        );
    }

    public void listBooks() {
        livroRepository.findAll().stream()
                .forEach(System.out::println);
    }
}
