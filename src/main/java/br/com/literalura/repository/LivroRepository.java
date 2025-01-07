package br.com.literalura.repository;

import br.com.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByPublicId(Long publicId);
}
