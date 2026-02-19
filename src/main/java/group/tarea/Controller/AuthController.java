package group.tarea.Controller;

import group.tarea.Config.Exception.Exceptions;
import group.tarea.Controller.models.AuthResponse;
import group.tarea.Controller.models.AuthenticationRequest;
import group.tarea.Controller.models.RegisterRequest;
import group.tarea.Domain.Service.AuthService;
import group.tarea.Domain.Service.JwtService;
import group.tarea.Domain.Service.UsuarioService;
import group.tarea.Persistence.Entity.User;
import group.tarea.Persistence.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    private UsuarioService userService;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody RegisterRequest request){
        try {
            // Intentar registrar al usuario
            AuthResponse authResponse = authService.register(request);
            return ResponseEntity.ok(authResponse);
        } catch (Exceptions.UserAlreadyExistsException e) {
            // Manejar usuario duplicado
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe.");

        } catch (Exception e) {
            // Manejar otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor.");
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            // Intentar autenticar al usuario usando el servicio
            AuthResponse authResponse = authService.authenticate(request);

            // Obtener el usuario desde el repositorio
            User user = userRepository.findByUser(request.getUser());



            // Si todo está bien, retornar la respuesta de autenticación
            return ResponseEntity.ok(authResponse);

        } catch (BadCredentialsException e) {
            // Manejar credenciales incorrectas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas. Verifique su usuario y contraseña.");

        }  catch (AuthenticationException e) {
            // Manejar errores generales de autenticación
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("No se pudo autenticar. Por favor, verifique los datos.");

        } catch (Exception e) {
            // Manejar errores generales (problemas en el servidor, etc.)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor. Por favor, intente más tarde.");
        }
    }




}
