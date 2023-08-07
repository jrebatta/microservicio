package microservicio.backend.controllers;

import microservicio.backend.interfaces.UsuarioRepository;
import microservicio.backend.clases.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usuario) {
        if (!usuario.getContraseña().equals(usuario.getConfirmarContraseña())) {
            return ResponseEntity.badRequest().body("Las contraseñas no coinciden");
        }

        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioRepository.deleteById(id);
            return "Usuario eliminado con éxito";
        } catch (Exception e){
            return "Usuario no existe";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUsuario(@RequestBody Map<String, String> loginData) {
        String user = loginData.get("user");
        String contraseña = loginData.get("contraseña");

        Usuario usuario = usuarioRepository.findByUser(user);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Usuario no encontrado"));
        }

        if (!usuario.getContraseña().equals(contraseña)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Contraseña incorrecta"));
        }

        // Generar un token JWT con una clave segura
        String token = Jwts.builder()
                .setSubject(user)
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256)) // Usar la clave segura
                .compact();

        // Retornar el token en el cuerpo de la respuesta
        Map<String, String> response = new HashMap<>();
        response.put("access_token", token);
        System.out.printf("access_token: " + token);
        return ResponseEntity.ok(response);
    }

}
