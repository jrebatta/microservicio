package game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class GameApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);
	}

	@GetMapping("/registro")
	public String mostrarPaginaRegistro() {
		return "registro";
	}

	@GetMapping("/login")
	public String mostrarPaginaLogin() {
		return "login";
	}

}