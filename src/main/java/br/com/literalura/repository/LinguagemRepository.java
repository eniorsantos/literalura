package br.com.literalura.repository;

import br.com.literalura.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinguagemRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByLanguage(String language);
}
