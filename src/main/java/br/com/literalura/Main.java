package br.com.literalura;

import br.com.literalura.service.LivroService;
import br.com.literalura.service.LinguagemService;
import br.com.literalura.service.AutorService;

import java.util.*;

public class Main {

    private Scanner leitura = new Scanner(System.in);

    private LivroService service;
    private AutorService autorService;
    private LinguagemService linguagemService;

    public Main(LivroService service, AutorService autorService, LinguagemService linguagemService) {
        this.service = service;
        this.autorService = autorService;
        this.linguagemService = linguagemService;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    
                    1 - Buscar livro pelo título
                    2 - Listar books registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar books em um determinado idioma
                    0 - Sair
                                               
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosEmAnoEspecifico();
                    break;
                case 5:
                    listarLivrosEmDeterminadoIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void listarLivrosEmDeterminadoIdioma() {
        System.out.println("Digite o idioma desejado da lista abaixo");
        linguagemService.listAllLanguages();

        String language = leitura.nextLine();

        linguagemService.listBooksByLanguage(language);
    }

    private void listarAutoresVivosEmAnoEspecifico() {
        System.out.println("Digite o ano que deseja saber");
        try {
            Integer ano = leitura.nextInt();
            leitura.nextLine();
            autorService.listAuthorsAliveInSpecifYear(ano);
        } catch (InputMismatchException e) {
            System.out.println("Ops, acho que digitou algo diferente de numeros ...");
        }

    }

    private void listarAutores() {
        System.out.println("Listando autores: ");
        autorService.listAuthors();
    }

    private void listarLivros() {
        System.out.println("Listando books: ");
        service.listBooks();
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Digite o nome do livro que busca");
        String livro = leitura.nextLine();
        service.addBook(livro);
    }
}