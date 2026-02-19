package group.tarea.Controller;

import group.tarea.Domain.Dto.UsuarioDto;
import group.tarea.Domain.Service.UsuarioService;
import group.tarea.Persistence.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/Usuario/")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/save")
    public UsuarioDto save(@RequestBody UsuarioDto usuarioDto){
        return usuarioService.save(usuarioDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getId(@PathVariable Long id){
        try {
            UsuarioDto usuarioDto = usuarioService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            return ResponseEntity.ok(usuarioDto);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("No autorizado");
        }

    }

    @GetMapping("/get")
    public List<UsuarioDto> get(){return usuarioService.getAll();}

    @GetMapping("/params")
    public ResponseEntity<?> getParams(
            @RequestParam String nombre,
            @RequestParam String apellido) {

        String nombres = nombre +" " + apellido;
        Map<String, String> response = new HashMap<>();
        response.put("nombreCompleto", nombres );

        return ResponseEntity.ok(response);
    }



}
