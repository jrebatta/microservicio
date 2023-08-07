package microservicio.backend.interfaces;

import microservicio.backend.clases.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUser(String user);

}
