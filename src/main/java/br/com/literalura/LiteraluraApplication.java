package br.com.literalura;

import br.com.literalura.service.LivroService;
import br.com.literalura.service.LinguagemService;
import br.com.literalura.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	@Autowired
	private LivroService service;
	@Autowired
	private AutorService autorService;
	@Autowired
	private LinguagemService linguagemService;

	public static void main(String[] args) {
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(service, autorService, linguagemService);
		main.exibeMenu();
	}
}
