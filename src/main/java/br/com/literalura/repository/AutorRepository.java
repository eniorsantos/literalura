package br.com.literalura.repository;

import br.com.literalura.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);
    @Query("SELECT p FROM Person p WHERE p.birthYear <= :year AND p.deathYear >= :year")
    Optional<List<Person>> findAuthorAliveInSpecifYear(Integer year);
}
