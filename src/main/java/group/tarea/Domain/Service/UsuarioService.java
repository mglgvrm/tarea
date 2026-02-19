package group.tarea.Domain.Service;



import group.tarea.Domain.Dto.UsuarioDto;
import group.tarea.Domain.Mapper.UsuarioMapper;
import group.tarea.Persistence.Entity.User;
import group.tarea.Persistence.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;


    public List<UsuarioDto> getAll() {
        return usuarioRepository.findAll().stream().map(UsuarioMapper::toDto).collect(Collectors.toList());
    }

    public Optional<UsuarioDto> findById(Long id) {
        return usuarioRepository.findById(id).map(UsuarioMapper::toDto);
    }

    public Optional<User> findByIds(Long id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioDto save(UsuarioDto usuarioDto) {
        usuarioRepository.save(UsuarioMapper.toEntity(usuarioDto));
        return usuarioDto;
    }


}
