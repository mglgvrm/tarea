package group.tarea.Domain.Service;


import group.tarea.Config.Exception.Exceptions;
import group.tarea.Controller.models.AuthResponse;
import group.tarea.Controller.models.AuthenticationRequest;
import group.tarea.Controller.models.RegisterRequest;
import group.tarea.Persistence.Entity.User;
import group.tarea.Persistence.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUser(request.getUser())) {
            throw new Exceptions.UserAlreadyExistsException("El usuario ya existe.");
        }

        var user = User.builder()
                .user(request.getUser())
                .name(request.getName())
                .lastName(request.getLastName())

                .build();
        userRepository.save(user);

        var jwtToken = jwtService.genereteToken((UserDetails) user);

        return AuthResponse.builder()
                .token(jwtToken)
                .name(user.getName().toUpperCase())
                .lastName(user.getLastName().toUpperCase())
                .build();

    }



    @Override
    public AuthResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUser(),
                        request.getPassword()
                )
        );
        UserDetails user = userRepository.findUserByUser(request.getUser()).orElseThrow();
        String jwtToken = jwtService.genereteToken(user);
        List<String> roles = user.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        return AuthResponse.builder()
                .token(jwtToken)
                .authorities(roles)

                .build();
    }

    @Override
    public User getCurrentUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }



}

